package com.kingdee.hrp.sms.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * mybatis逆向工程生成javabean Mapper MapperBean
 *
 * @author yadda
 */
public class MybatisGeneratorMain {

    public static void main(String[] args) throws Exception {

        List<String> warnings = new ArrayList<String>();

        // 覆盖生成
        boolean overwrite = true;

        String rootPath = System.getProperty("user.dir");
        String configPath = Thread.currentThread().getContextClassLoader().getResource("generatorConfig.xml").getPath();
        File configFile = new File(configPath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        // Context上下文
        Context context = config.getContexts().get(0);
        // 移除xml配置文件中的配置的table，table元素配置来自于csv文件
        context.getTableConfigurations().clear();
        // 加载table元素配置
        loadFromCsv(context);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                new ShellCallback(rootPath + File.separator + "sms-cloud", overwrite), warnings);
        myBatisGenerator.generate(new VerboseProgressCallback());
    }

    public static List<TableConfiguration> loadFromCsv(Context context) throws Exception {

        // 加载table元素配置文件
        String configPath = Thread.currentThread().getContextClassLoader()
                .getResource("mybatis-generator-table-config.csv").getPath();
        File tableConfigFile = new File(configPath);

        List<TableConfiguration> list = new ArrayList<>();

        // 便利配置文件获取table元素配置
        if (tableConfigFile.exists() && tableConfigFile.isFile()) {

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(tableConfigFile));
                String line = "";
                int lineIndex = 0;
                while ((line = br.readLine()) != null) {
                    lineIndex++;
                    if (lineIndex == 1) {
                        // 第一行是标题行
                        continue;
                    }
                    TableConfiguration tableConfiguration = toConfig(line, context);
                }
            } finally {
                IOUtils.closeQuietly(br);
            }
        }
        return list;
    }

    private static TableConfiguration toConfig(String line, Context context) {

        if (StringUtils.isBlank(line)) {
            return null;
        }
        String[] strings = line.split(",");

        // 第一列为开关，只是是否操作该行
        boolean enable = Boolean.valueOf(strings[0]);
        // 第二列表名
        String tableName = strings[1];

        if (!enable || StringUtils.isBlank(tableName)) {
            return null;
        }

        String schema = strings[2];
        String catalog = strings[3];
        String domainObjectName = strings[4];
        String mapperName = strings[5];
        boolean enableInsert = Boolean.valueOf(strings[6]);
        boolean enableSelectByPrimaryKey = Boolean.valueOf(strings[7]);
        boolean enableSelectByExample = Boolean.valueOf(strings[8]);
        boolean enableUpdateByPrimaryKey = Boolean.valueOf(strings[9]);
        boolean enableDeleteByPrimaryKey = Boolean.valueOf(strings[10]);
        boolean enableDeleteByExample = Boolean.valueOf(strings[11]);
        boolean enableCountByExample = Boolean.valueOf(strings[12]);
        boolean enableUpdateByExample = Boolean.valueOf(strings[13]);
        boolean delimitAllColumns = Boolean.valueOf(strings[14]);

        // 构建table配置
        TableConfiguration config = new TableConfiguration(context);

        config.setTableName(tableName);
        config.setSchema(StringUtils.trimToNull(schema));
        config.setCatalog(StringUtils.trimToNull(catalog));
        config.setDomainObjectName(StringUtils.trimToNull(domainObjectName));
        config.setMapperName(StringUtils.trimToNull(mapperName));
        config.setInsertStatementEnabled(enableInsert);
        config.setCountByExampleStatementEnabled(enableCountByExample);
        config.setDeleteByExampleStatementEnabled(enableDeleteByExample);
        config.setDeleteByPrimaryKeyStatementEnabled(enableDeleteByPrimaryKey);
        config.setSelectByExampleStatementEnabled(enableSelectByExample);
        config.setSelectByPrimaryKeyStatementEnabled(enableSelectByPrimaryKey);
        config.setUpdateByExampleStatementEnabled(enableUpdateByExample);
        config.setUpdateByPrimaryKeyStatementEnabled(enableUpdateByPrimaryKey);
        config.setAllColumnDelimitingEnabled(delimitAllColumns);

        context.getTableConfigurations().add(config);

        return config;
    }

    public static class ShellCallback extends DefaultShellCallback {

        private String rootPath;

        public ShellCallback(boolean overwrite) {
            super(overwrite);
        }

        public ShellCallback(String rootPath, boolean overwrite) {
            super(overwrite);
            this.rootPath = rootPath;
        }

        @Override
        public File getDirectory(String targetProject, String targetPackage) throws ShellException {
            File project = new File(rootPath + File.separator + targetProject);
            if (!project.isDirectory()) {
                //$NON-NLS-1$
                throw new ShellException(getString("Warning.9",
                        targetProject));
            }

            StringBuilder sb = new StringBuilder();
            //$NON-NLS-1$
            StringTokenizer st = new StringTokenizer(targetPackage, ".");
            while (st.hasMoreTokens()) {
                sb.append(st.nextToken());
                sb.append(File.separatorChar);
            }

            File directory = new File(project, sb.toString());
            if (!directory.isDirectory()) {
                boolean rc = directory.mkdirs();
                if (!rc) {
                    //$NON-NLS-1$
                    throw new ShellException(getString("Warning.10",
                            directory.getAbsolutePath()));
                }
            }

            return directory;
        }
    }

    public static class ServicePlugin extends PluginAdapter {

        @Override
        public boolean validate(List<String> warnings) {
            return false;
        }

    }
}
