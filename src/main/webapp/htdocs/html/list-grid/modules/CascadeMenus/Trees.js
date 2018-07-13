

/**
* 分类导航树的数据模块
* 
*/
define('CascadeMenus/Trees', [

    //level=0, index=0
    {
        name: '全部', // level = 0
        items: [

            {
                name: '江西省',
                items: [
                    {
                        name: '南昌市',
                        items: [
                            { name: '东湖区' },
                            { name: '西湖区' },
                            { name: '青云谱区' },
                        ],
                    },
                    {
                        name: '吉安市',
                        items: [
                            { name: '吉州区' },
                            { name: '青原区' },
                            { name: '吉安县' },
                            { name: '峡江县' },
                        ],
                    },
                ]
            },
            {
                name: '广东省', // level = 1
                items: [
                    {
                        name: '广州市',
                        items: [
                            { name: '天河区' },
                            { name: '白云区' },
                            { name: '黄埔区' },
                            { name: '番禺区' },
                            { name: '花都区' },
                            { name: '南沙区' },
                            { name: '萝岗区' },
                        ],
                    },
                    {
                        name: '深圳市', // level = 2
                        items: [
                            { name: '南山区' }, // level = 3
                            { name: '宝安区' },
                            { name: '福田区' },
                            { name: '罗湖区' },
                            { name: '龙岗区' },
                        ],
                    },
                    {
                        name: '茂名市', // level = 2
                        items: [
                            { name: '电白县' }, // level = 3
                            { name: '高州市' },
                            { name: '茂南区' },
                            { name: '信宜市' },
                            { name: '化州市' },
                        ],
                    },
                ]
            },
            {
                name: '湖南省',
                items: [
                    {
                        name: '长沙市',
                    },
                    {
                        name: '株洲市',
                    },
                    {
                        name: '湘潭市',
                    },
                ]
            },
        ]
    },

    //level=0, index = 1
    {
        name: '全部', // level = 0
        items: [

            {
                name: '江西省',
                items: [
                    {
                        name: '南昌市',
                        items: [
                            { name: '东湖区' },
                            { name: '西湖区' },
                            { name: '青云谱区' },
                        ],
                    },
                    {
                        name: '吉安市',
                        items: [
                            { name: '吉州区' },
                            { name: '青原区' },
                            { name: '吉安县' },
                            { name: '峡江县' },
                        ],
                    },
                ]
            },
            {
                name: '广东省', // level = 1
                items: [
                    {
                        name: '广州市',
                        items: [
                            { name: '天河区' },
                            { name: '白云区' },
                            { name: '黄埔区' },
                            { name: '番禺区' },
                            { name: '花都区' },
                            { name: '南沙区' },
                            { name: '萝岗区' },
                        ],
                    },
                    {
                        name: '深圳市', // level = 2
                        items: [
                            { name: '南山区' }, // level = 3
                            { name: '宝安区' },
                            { name: '福田区' },
                            { name: '罗湖区' },
                            { name: '龙岗区' },
                        ],
                    },
                    {
                        name: '茂名市', // level = 2
                        items: [
                            { name: '电白县' }, // level = 3
                            { name: '高州市' },
                            { name: '茂南区' },
                            { name: '信宜市' },
                            { name: '化州市' },
                        ],
                    },
                ]
            },
            {
                name: '湖南省',
                items: [
                    {
                        name: '长沙市',
                    },
                    {
                        name: '株洲市',
                    },
                    {
                        name: '湘潭市',
                    },
                ]
            },
        ]
    },
]);






    