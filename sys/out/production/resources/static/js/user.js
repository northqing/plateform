/**
 * Created by Administrator on 2018/1/26.
 */

$(function () {
    $("#userTable").dataTable({
        ajax: {
            url: '/user/list',
            type: 'post',
            data: function (d) {
                d.size = d.length;
                d.page = d.start;
                d.name = d.search.value;
            },
            dataFilter: function (data) {
                debugger;
                var json = jQuery.parseJSON(data);
                json.recordsTotal = json.totalElements;
                json.recordsFiltered = json.numberOfElements;
                json.data = json.content;

                return JSON.stringify(json);
            },
        },
        autoWidth: true,
        serverSide: true,
        columns: [
            {defaultContent: ''},
            {data: 'username'},
            {data: 'name'},
            {data: 'department'},
            {data: 'state'},
            {data: 'lastLoginDate'},
            {defaultContent: ''},
        ],
        columnDefs: [
            {
                targets: 0,
                data: 'id',
                render: function (data, type, full) {
                    return "<input type='checkbox' value='" + data + "' name='id'>";
                }
            },
            {
                targets: 6,
                data: 'id',
                render: function (data, type, full) {
                    return "<a href='#' onclick='editUser(" + data + ")'>编辑</a>";
                }
            }
        ],
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
    });

    $("#add-user").click(function () {
        addUser();
    });
});

function addUser() {
    ShowUserDialog("/user/add");
}

function editUser(userId) {
    ShowUserDialog('/user/edit?id=' + userId);
}

function ShowUserDialog(url) {
    top.showWindow({
        id: "userInfo",
        title: '用户信息',
        width: '600px',
        height: '400px',
        modal: true,
        autoOpen: false,
        href:url,
    });
}



