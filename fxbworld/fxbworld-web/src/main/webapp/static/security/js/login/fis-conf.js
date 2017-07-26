 // 开启autoCombine可以将零散资源进行自动打包
    fis.config.set('modules.parser.scss', 'node-sass');

    // 某些资源从构建中去除
    fis.set('project.ignore', [
	  'WEB-INF/**',
	  'dist/**',
	  '.project',
	  'fis-conf.js',
	  'favicon.ico'
    ]);


   
    // 添加MD5戳，用于强刷缓存
    fis.match('*.{png,js,css}', {
        useHash: false  
    });

    fis.match('*.js', {
        optimizer: fis.plugin('uglify-js')
    });
