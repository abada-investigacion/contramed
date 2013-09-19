/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.namespace('Ext.contramed');

Ext.contramed.BarcodeButton = Ext.extend(Ext.Component, {
    height:50,
    running: false,
    nonStopRead:false,
    constructor: function(cfg) {
        Ext.apply(this, cfg);
        Ext.contramed.BarcodeButton.superclass.constructor.call(this, cfg);

        this.addEvents('read','click');
    },
    onRender: function(ct, position) {
        var tplTemplate = '<div id=\"'+this.id+'\"><video id=\"' + this.getNameVideo() + '\" height=\"'+this.height+'\" autoplay></video>'//
                + '<canvas id=\"' + this.getNameCanvas() + '\" style=\"display:none;\"></canvas></div>';
        this.tpl = new Ext.XTemplate(tplTemplate);
        if (position) {
            this.tpl.insertBefore(position, undefined, true);
        } else {
            this.tpl.append(ct, undefined, true);
        }

        this.task = {
            run: function() {
                this.captureImage();
            },
            scope: this,
            interval: 1000
        };

        Ext.contramed.BarcodeButton.superclass.onRender.call(this, ct, position);

        this.setup();

        this.addListener('click', this.onButtonClick, this);
    },
    getNameVideo: function() {
        return this.id + 'Video';
    },
    getNameCanvas: function() {
        return this.id + 'Canvas';
    },
    setup: function() {
        var me = this;
        var video = document.getElementById(this.getNameVideo());

        navigator.myGetMedia = (navigator.getUserMedia ||
                navigator.webkitGetUserMedia ||
                navigator.mozGetUserMedia ||
                navigator.msGetUserMedia);
        navigator.myGetMedia({video: true}, function(stream) {
            window.URL = window.URL || window.webkitURL;
            video.src = window.URL ? window.URL.createObjectURL(stream) : stream;
//            video.play();
            video.addEventListener('click', function() {
                me.fireEvent('click', me);
            }, false);
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
        this.onReadImage( dataURL);
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
                if (!this.nonStopRead){
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
    onButtonClick: function() {
        if (!this.running) {
            Ext.get(this.id).fadeIn({remove: false, useDisplay: true, endOpacity: 1, duration: 2});
            Ext.TaskMgr.start(this.task);
            this.running = true;
        } else {
            Ext.get(this.id).fadeOut({remove: false, useDisplay: true, endOpacity: 0.5, duration: 2});
            Ext.TaskMgr.stop(this.task);
            this.running = false;
        }
    }, forceRead: function() {
        this.fireEvent('click');
    }
});
