
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    // console.log(questionId);
    // console.log(content);
    if (!content){
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200){
                window.location.reload();
                // $("#comment_section").hide();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=7f746da9e0679e7eda95&redirect_uri=http://localhost:8888/callback&scope=user&state=1")
                        //涉及到前端的Web存储
                        window.localStorage.setItem("closable", true);
                    }

                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });

}