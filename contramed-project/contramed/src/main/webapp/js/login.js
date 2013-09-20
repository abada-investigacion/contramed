Ext.onReady(function() {

    Ext.QuickTips.init();

    var header = new Ext.Panel({
        id: 'header-panel',
        region: 'north',
        frame: true,
        height: 80,
        html:
                '<div class=\"x-panel-header\">' +
                '<div style=\"float:left;height:70px;\"><img alt=\" \" src=\"images/logos/contramed.jpg\" height=\"65\" width=\"300\"/></div>' +
                '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/abada.png\" height=\"40\" /></div>' +
                '<div style=\"clear:both; \" />' +
                '</div>'

    });

    new Ext.Viewport({
        title: 'Border Layout',
        items: [
            header
        ]
    });

    function formSubmit() {
        if (login.getForm().isValid()) {
            login.getForm().submit({
                method: 'POST',
                waitTitle: 'Conectando',
                waitMsg: 'Comprobando usuario y contrase&ntilde;a...',
                failure: function(form, action) {
                    Ext.Msg.alert('Error', 'Nombre de usuario o contrase&ntilde;a incorrectos.');
                    login.getForm().reset();
                },
                success: function() {
                    window.location = 'main.htm';
                }
            });
        }
    }

    var barcode = new Ext.contramed.BarcodeButton({       
        height:30,
        listeners: {
            read: function(data) {
                Ext.abada.Ajax.requestJson({
                    url: 'getUserName.htm',
                    //zzurl:'getUserNameByFinger.htm',
                    scope: this,
                    waitTitle: 'Conectando',
                    waitMsg: 'Comprobando usuario y contrase&ntilde;a...',
                    params: {
                        tag: data
                    },
                    failure: function() {
                        //login.get('username_login').setValue('');
                        Ext.Msg.alert('Error', 'Nombre de usuario o contrase&ntilde;a incorrectos.');
                        barcode.forceRead();
                    },
                    success: function(storeData) {
                        //login.get('username_login').setValue(storeData.data[0]);
                        window.location = 'main.htm';
                    }
                });
            }
        }
    });
    barcode.setTooltip('Logear');

    var login = new Ext.form.FormPanel({
        frame: true,
        width: 400,
        height: 150,
        region: 'center',
        title: 'Acceder',
        url: 'loginvalidation.htm',
        defaultType: 'textfield',
        monitorValid: true,
        items: [
            {
                fieldLabel: 'Nombre de Usuario',
                name: 'login',
                id: 'username_login',
                allowBlank: false,
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Introduzca su nombre de usuario'
                        });
                    }
                }
            }, {
                fieldLabel: 'Contrase&ntilde;a',
                name: 'password',
                allowBlank: false,
                inputType: 'password',
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Introduzca tu contrase&ntilde;a'
                        });
                    }
                }
            }
        ],
        buttons: [{
                //text:'Acceso',
                id: 'blogin',
                iconCls: 'sesion',
                scale: 'large',
                formBind: true,
                handler: formSubmit,
                tooltip: 'Acceso a la aplicaci&oacute;n'
            }
            , barcode
//        ,MCAButton
        ]
    });

    var win = new Ext.Window({
        layout: 'form',
        id: 'wlogin',
        width: 400,
        height: 160,
        closable: false,
        resizable: false,
        plain: true,
        border: false,
        items: [login]
    });

    win.show();

    new Ext.KeyNav('wlogin', {
        'enter': formSubmit,
        scope: login
    });

    barcode.forceRead();
//    MCAButton.forceRead();
});