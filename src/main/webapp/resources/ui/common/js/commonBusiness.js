//打开标签选择窗口
function openLabelSelectDialog(obj) {

    var src = "business/dialog/labelInit.htm?kindsId=" + obj.kindsId;
    $.ligerDialog.open({
        name: "select_label_dialog",
        title: "选择标签",
        height: 350,
        url: src,
        width: 700,
        showMax: true,
        showToggle: true,
        showMin: true,
        isResize: true,
        modal: true,
        buttons: [
            {
                text: '确定', onclick: function (item, dialog) {
                var data = $("#select_label_dialog")[0].contentWindow.h2y_returnData();
                if (data) {
                    h2y_labelSelectCallBack(data);
                    dialog.close();
                }
            }
            },
            {
                text: '取消', onclick: function (item, dialog) {
                dialog.close();
            }
            }
        ]
    });
}


//打开商品选择窗口
function openGoodsSelectDialog(obj) {

    var selectType = "radio";
    if (obj != null && obj.selectType != null) {
        selectType = obj.selectType;
    }

    var src = "business/dialog/selectInit.htm?selectType=" + selectType;
    $.ligerDialog.open({
        name: "select_goods_dialog",
        title: "选择商品",
        height: 450,
        url: src,
        width: 700,
        showMax: true,
        showToggle: true,
        showMin: true,
        isResize: true,
        modal: true,
        checkbox:true,
        buttons: [
            {
                text: '确定', onclick: function (item, dialog) {
                var data = $("#select_goods_dialog")[0].contentWindow.h2y_returnData();
                if (data) {
                    h2y_goodsSelectCallBack(data);
                    dialog.close();
                }
            }
            },
            {
                text: '取消', onclick: function (item, dialog) {
                dialog.close();
            }
            }
        ]
    });
}

//打开广告主题选择窗口openAdvertSubjectSelectDialog
function openAdvertSubjectSelectDialog(type) {

    var src = "activity/dialog/subjectInit.htm?type="+type;
    $.ligerDialog.open({
        name: "select_advert_subject",
        title: "选择广告主题",
        height: 500,
        url: src,
        width: 700,
        showMax: true,
        showToggle: true,
        showMin: true,
        isResize: true,
        modal: true,
        buttons: [
            {
                text: '确定', onclick: function (item, dialog) {
                var data = $("#select_advert_subject")[0].contentWindow.h2y_returnData();
                if (data) {
                    h2y_advertSubjectSelectCallBack(data);
                    dialog.close();
                }
            }
            },
            {
                text: '取消', onclick: function (item, dialog) {
                dialog.close();
            }
            }
        ]
    });
}

//打开活动商品选择
function openActivityGoodsSelectDialog(kindId) {
    var kindsId=kindId;
    var src = "activity/dialog/activityGoodsInit.htm?kindId="+kindsId;
    $.ligerDialog.open({
        name: "select_activity_goods_dialog",
        title: "选择商品",
        height: 450,
        url: src,
        width: 700,
        showMax: true,
        showToggle: true,
        showMin: true,
        isResize: true,
        modal: true,
        checkbox:true,
        buttons: [
            {
                text: '确定', onclick: function (item, dialog) {
                var data = $("#select_activity_goods_dialog")[0].contentWindow.h2y_returnData();
                if (data) {
                    h2y_activityGoodsSelectCallBack(data);
                    dialog.close();
                }
            }
            },
            {
                text: '取消', onclick: function (item, dialog) {
                dialog.close();
            }
            }
        ]
    });
}
