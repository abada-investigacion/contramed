/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.namespace('Ext.contramed');

Ext.contramed.BarcodeButton = Ext.extend(Ext.Component, {
    height: 50,
    running: false,
    nonStopRead: false,
    tooltipType: 'qtip',
    autoLoad: false,
    ctype: 'Ext.contramed.BarcodeButton',
    iconCls:'',
    constructor: function(cfg) {
        Ext.apply(this, cfg);
        Ext.contramed.BarcodeButton.superclass.constructor.call(this, cfg);

        if (this.tooltip)
            this.setTooltip(this.tooltip, true);
        this.addEvents('read', 'click');

        this.addListener('hide', this.onHide, this);
        this.addListener('disable', this.onHide, this);
        this.addListener('destroy', this.onHide, this);
    },
    onHide: function() {
        if (this.running) {
            Ext.TaskMgr.stop(this.task);
        }
    },
    onRender: function(ct, position) {
        var me = this;
        Ext.contramed.BarcodeButton.superclass.onRender.call(this, ct, position);

        var tplTemplate = '<canvas id=\"' + this.getNameCanvas() + '\" style=\"display:none;\"></canvas>';
        this.tpl = new Ext.XTemplate(tplTemplate);

        var div = Ext.get(this.id);
        this.tpl.append(div, undefined, true);

        this.task = {
            run: function() {
                this.captureImage();
            },
            scope: this,
            interval: 1000
        };

        this.addListener('click', this.onButtonClick, this);

        this.setTooltip(this.tooltip, true);
        if (this.autoLoad)
            this.onButtonClick();
    },
    getNameVideo: function() {
        return this.id + 'Video';
    },
    getNameCanvas: function() {
        return this.id + 'Canvas';
    },
    setup: function() {
        var video = this.addVideo();

        navigator.myGetMedia = (navigator.getUserMedia ||
                navigator.webkitGetUserMedia ||
                navigator.mozGetUserMedia ||
                navigator.msGetUserMedia);
        navigator.myGetMedia({video: true}, function(stream) {
            window.URL = window.URL || window.webkitURL;
            video.src = window.URL ? window.URL.createObjectURL(stream) : stream;
//            video.play();            
        }, this.error);
    },
    error: function(e) {
        console.log(e);
    },
    captureImage: function() {
        var video = document.getElementById(this.getNameVideo());
        var canvas = document.getElementById(this.getNameCanvas());
        var ctx = canvas.getContext('2d');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        ctx.drawImage(video, 0, 0);
//save canvas image as data url
        var dataURL = canvas.toDataURL();
        this.onReadImage(dataURL);
////set preview image src to dataURL
//        document.getElementById('preview').src = dataURL;
//// place the image value in the text box
//        document.getElementById('imageToForm').value = dataURL;
    },
    onReadImage: function(data) {
//        data = data.replace('data:image/png;base64,', '');
        //to decoder barcode
        Ext.Ajax.request({
            url: 'decode.htm',
            scope: this,
            success: function(response) {
                if (!this.nonStopRead) {
                    this.onButtonClick();
                }
                this.fireEvent('read', response.responseText);
            },
            headers: {
                'Content-Type': 'text/plain'
            },
            params: data
        });
    },
    addVideo: function() {
        var img = Ext.get(this.getNameVideo());
        if (img)
            img.remove();

        var me = this;
        var video = document.createElement('video');
        video.setAttribute('autoplay', 'true');
        video.setAttribute('height', this.height);
        video.setAttribute('id', this.getNameVideo());
        video.setAttribute('class',this.iconCls);
        video.addEventListener('click', function() {
            me.fireEvent('click', me);
        }, false);


        var canvas = Ext.get(this.getNameCanvas());
        Ext.get(video).insertBefore(canvas);

        if (this.tooltip)
            this.setTooltip(this.tooltip, false);
        return video;
    },
    removeVideo: function() {
        Ext.get(this.getNameVideo()).remove();

        var me = this;
        var img = document.createElement('button');        
        img.setAttribute('style', 'height:'+this.height);        
        img.setAttribute('id', this.getNameVideo());
        img.setAttribute('class',this.iconCls);      
        img.addEventListener('click', function() {
            me.fireEvent('click', me);
        }, false);
        var canvas = Ext.get(this.getNameCanvas());
        Ext.get(img).insertBefore(canvas);

        if (this.tooltip)
            this.setTooltip(this.tooltip, false);
    },
    onButtonClick: function() {
        if (this.rendered) {
            if (!this.running) {
                this.setup();
                Ext.get(this.id).fadeIn({remove: false, useDisplay: true, endOpacity: 1, duration: 2});
                Ext.TaskMgr.start(this.task);
                this.running = true;
            } else {
                this.removeVideo();
                Ext.get(this.id).fadeOut({remove: false, useDisplay: true, endOpacity: 0.5, duration: 2});
                Ext.TaskMgr.stop(this.task);
                this.running = false;
            }
            return true;
        } else {
            return false;
        }
    }, forceRead: function() {
        if (!this.onButtonClick()) {
            this.autoLoad = true;
        }
    }, setTooltip: function(tooltip, /* private */ initial) {
        this.tooltip = tooltip;
        if (this.rendered) {
            if (!initial) {
                this.clearTip();
            }
            var e1 = document.getElementById(this.getNameVideo());
            if (e1) {
                if (Ext.isObject(tooltip)) {
                    Ext.QuickTips.register(Ext.apply({
                        target: e1.id
                    }, tooltip));
                } else {
                    e1[this.tooltipType] = tooltip;
                }
            }
        }
        return this;
    }, // private
    clearTip: function() {
        if (Ext.isObject(this.tooltip)) {
            var video = document.getElementById(this.getNameVideo());
            Ext.QuickTips.unregister(video);
        }
    }
});
