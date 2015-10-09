var gulp = require("gulp"),
	less = require("gulp-less"),
	autoprefixer = require('gulp-autoprefixer'),
	plumber = require('gulp-plumber');

gulp.task("styles", function() {
    gulp.src("less/style.less")
    	.pipe(plumber())
    	.pipe(less())
    	.pipe(autoprefixer('last 5 version'))
    	.pipe(gulp.dest("css/."));
});

gulp.task("watch", function() {
	gulp.watch("less/*.less", ["styles"]);
});

