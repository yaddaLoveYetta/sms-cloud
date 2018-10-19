$("#input-pd").fileinput({
    uploadUrl: "/file-upload-batch/1",
    uploadAsync: false,
    minFileCount: 2,
    maxFileCount: 5,
    overwriteInitial: false,
    initialPreview: [
        // IMAGE DATA
        "http://kartik-v.github.io/bootstrap-fileinput-samples/samples/Desert.jpg",
        // IMAGE DATA
        "http://kartik-v.github.io/bootstrap-fileinput-samples/samples/Lighthouse.jpg",
        // VIDEO DATA
        "http://kartik-v.github.io/bootstrap-fileinput-samples/samples/small.mp4",
        // OFFICE WORD DATA
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/SampleDOCFile_100kb.doc',
        // OFFICE EXCEL DATA
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/SampleXLSFile_38kb.xls',
        // OFFICE POWERPOINT DATA
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/SamplePPTFile_500kb.ppt',
        // TIFF IMAGE FILE
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/multipage_tiff_example.tif',
        // ADOBE ILLUSTRATOR FILE
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/sample_ai.ai',
        // ENCAPSULATED POST SCRIPT FILE
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/sample_eps.eps',
        // PDF DATA
        'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/pdf-sample.pdf',
        // TEXT DATA
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ut mauris ut libero fermentum feugiat eu et dui. Mauris condimentum rhoncus enim",
    ],
    initialPreviewAsData: true, // identify if you are sending preview data only and not the raw markup
    initialPreviewFileType: 'image', // image is the default and can be overridden in config below
    initialPreviewDownloadUrl: 'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/{filename}',
    // includes the dynamic `filename` tag to be replaced for each config
    initialPreviewConfig: [
        {caption: "Desert.jpg", size: 827000, width: "120px", url: "/file-upload-batch/2", key: 1},
        {caption: "Lighthouse.jpg", size: 549000, width: "120px", url: "/file-upload-batch/2", key: 2},
        {
            type: "video",
            size: 375000,
            filetype: "video/mp4",
            caption: "KrajeeSample.mp4",
            url: "/file-upload-batch/2",
            key: 3,
            downloadUrl: 'http://kartik-v.github.io/bootstrap-fileinput-samples/samples/small.mp4', // override url
            filename: 'KrajeeSample.mp4' // override download filename
        },
        {type: "office", size: 102400, caption: "SampleDOCFile_100kb.doc", url: "/file-upload-batch/2", key: 4},
        {type: "office", size: 45056, caption: "SampleXLSFile_38kb.xls", url: "/file-upload-batch/2", key: 5},
        {type: "office", size: 512000, caption: "SamplePPTFile_500kb.ppt", url: "/file-upload-batch/2", key: 6},
        {type: "office", size: 811008, caption: "multipage_tiff_example.tif", url: "/file-upload-batch/2", key: 7},
        {type: "office", size: 375808, caption: "sample_ai.ai", url: "/file-upload-batch/2", key: 8},
        {type: "office", size: 40960, caption: "sample_eps.eps", url: "/file-upload-batch/2", key: 9},
        {type: "pdf", size: 8000, caption: "About.pdf", url: "/file-upload-batch/2", key: 10, downloadUrl: false}, // disable download
        {type: "text", size: 1430, caption: "LoremIpsum.txt", url: "/file-upload-batch/2", key: 11, downloadUrl: false},  // disable download
        {type: "html", size: 3550, caption: "LoremIpsum.html", url: "/file-upload-batch/2", key: 12, downloadUrl: false}  // disable download
    ],
    purifyHtml: true, // this by default purifies HTML data for preview
    uploadExtraData: {
        img_key: "1000",
        img_keywords: "happy, places"
    }
}).on('filesorted', function (e, params) {
    console.log('File sorted params', params);
}).on('fileuploaded', function (e, params) {
    console.log('File uploaded params', params);
});