$(function(){
    $("#topBtn").click(setTop);
    // $("#unTopBtn").click(unSetTop);
    $("#wonderfulBtn").click(setWonderful);
    // $("#unWonderfulBtn").click(unSetWonderful);
    $("#deleteBtn").click(setDelete);
});

function like(btn, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':"赞");
            } else {
                alert(data.msg);
            }
        }
    );
}

// // 置顶
// function setTop() {
//     $.post(
//         CONTEXT_PATH + "/discuss/top",
//         {"id":$("#postId").val()},
//         function(data) {
//             data = $.parseJSON(data);
//             if(data.code == 0) {
//                 $("#topBtn").attr("disabled", "disabled");
//             } else {
//                 alert(data.msg);
//             }
//         }
//     );
// }
// function unSetTop() {
//     $.post(
//         CONTEXT_PATH + "/discuss/untop",
//         {"id":$("#postId").val()},
//         function(data) {
//             data = $.parseJSON(data);
//             if(data.code == 0) {
//                 $("#unTopBtn").attr("disabled", "disabled");
//             } else {
//                 alert(data.msg);
//             }
//         }
//     );
// }
//
// // 加精
// function setWonderful() {
//     $.post(
//         CONTEXT_PATH + "/discuss/wonderful",
//         {"id":$("#postId").val()},
//         function(data) {
//             data = $.parseJSON(data);
//             if(data.code == 0) {
//                 $("#wonderfulBtn").attr("disabled", "disabled");
//             } else {
//                 alert(data.msg);
//             }
//         }
//     );
// }
// function unSetWonderful() {
//     $.post(
//         CONTEXT_PATH + "/discuss/unwonderful",
//         {"id":$("#postId").val()},
//         function(data) {
//             data = $.parseJSON(data);
//             if(data.code == 0) {
//                 $("#unWonderfulBtn").attr("disabled", "disabled");
//             } else {
//                 alert(data.msg);
//             }
//         }
//     );
// }

// 置顶与取消置顶
function setTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#topBtn").text(data.type==1?'取消置顶':'置顶');
            } else {
                alert(data.msg);
            }
        }
    );
}

// 加精与取消加精
function setWonderful() {
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#wonderfulBtn").text(data.status==1?'取消加精':'加精');
            } else {
                alert(data.msg);
            }
        }
    );
}

// 删除
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                location.href = CONTEXT_PATH + "/index";
            } else {
                alert(data.msg);
            }
        }
    );
}