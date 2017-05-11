/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function autoImgSize(imgs){
    imgs.each(function (){
        var img = document.createElement("img");
        var target = this;
        $(img).load(function (){      
            var hrate = target.height / this.height;
            var wrate = target.width / this.width;
            if(hrate < wrate){
                var nwidth = this.width * hrate;
                var marginLeft = (target.width - nwidth) / 2;
                $(target).css("margin-left",marginLeft);
                target.removeAttribute("width");
            }else{
                var nheight = this.height * wrate;
                var marginTop = (target.height - nheight) / 2;
                $(target).css("margin-top",marginTop);
                target.removeAttribute("height");
            }
        })
        img.src = this.src;
    });
}

