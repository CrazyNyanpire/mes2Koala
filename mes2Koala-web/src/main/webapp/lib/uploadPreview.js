$.fn.extend({
    uploadPreview: function (_opts) {
        var _self = this,
            _this = $(this);
        _opts = jQuery.extend({
            Img: "ImgPr",
            Width: 200,
            Height: 200,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {}	
        }, _opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file);
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file);
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file);
            }
            return url;
        };
        _this.change(function () {
            if(this.value) {    	
                if (!RegExp("\.(" + _opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    alert("选择文件错误,图片类型必须是" + _opts.ImgType.join("，") + "中的一种");
                    this.value = "";
                    return false;
                }
            $("#" + _opts.Img).attr('src', _self.getObjectURL(this.files[0]));
            //    _opts.Callback();
                
            }
        })
    }
});
