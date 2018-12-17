/**
 * Created by Administrator on 2018/1/25.
 */

/**
 * 获取用户菜单
 */
function loadUserMenu() {
    $.getJSON('/a/user/getMenu', null, function (data) {
            $('#menuItem').replaceWith(buildTreeMenu(data));
        }
    )
}

/**
 * 构建树菜单
 * @param data 菜单项JSON数据
 * @returns {string}
 */
function buildTreeMenu(data) {
    if (data == null) {
        return ""
    }
    var element = "";
    if (data.children.length > 0) {
        element = '<ul class="treeview-menu">';
        data.children.forEach(function (child) {
            element += buildTreeMenu(child);
        })
        element += "</ul>"
        element = '<li class="treeview">\
            <a href="#">\
            <i class="fa fa-dashboard"></i> <span>' + data.name + '</span>\
            <span class="pull-right-container">\
            <i class="fa fa-angle-left pull-right"></i>\
            </span>\
            </a>'
            + element
            + '</li>';
    }
    else {
        element = '<li><a href="'+ data.url + '" target ="mainFrame"><i class="fa fa-circle-o"></i>' + data.name + '</a></li>';
    }
    return element;
}

$(function () {
    loadUserMenu();
})

function createDialog(id, opts) {
    $(id).remove();
    $("<div id='"+ opts.id+ "'><iframe src='" + opts.href + "' style='width: 100%; height: 90%;' frameborder='0' scrolling='auto'></iframe></div>").appendTo("body");
    $(id).dialog(opts);
}

function showWindow(opts) {
    //var id = "#" + opts.id;
    //createDialog(id, opts);
    //$(id).dialog('open');

    var dialog = bootbox.dialog({
        size:"large",
        title:opts.title,
        message:"<div style='height: "+ opts.height + "'><iframe src='" + opts.href + "' style='width: 100%; height: 90%;' frameborder='0' scrolling='auto'></iframe></div>",
        buttons: {
            cancel: {
                label: "取消",
                className: 'btn-danger',
                callback: function(){
                }
            },
            ok: {
                label: "确定",
                className: 'btn-success',
                callback: function(){
                }
            }
        }
    });
}
