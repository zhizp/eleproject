<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/static/static.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=2.0, minimum-scale=0.5">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <title>欢迎登录</title>
    <link rel="stylesheet" href="${ctx}/common/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}/common/element.css"/>
    <link rel="stylesheet" href="${ctx}/css/base.css"/>
    <script src="${ctx}/common/vue.js"></script>
    <script src="${ctx}/common/vue-resource.min.js"></script>
    <script src="${ctx}/common/element.js"></script>
    <script src="${ctx}/common/store.modern.min.js"></script>
    <script src="${ctx}/js/base.js"></script>

    <style>
        body {
            padding: 0;
            margin: 0;
        }

        body, input {
            font-family: sans-serif;
        }

        .pad {
            padding-left: 15%;
            padding-right: 15%;
        }

        #login_main .el-tabs__nav {
            width: 100%;
        }

        #login_main .el-tabs__item {
            width: 101%; /*51%;*/
            text-align: center;
            font-size: 14pt;
        }

        #login_bottom {
            text-align: center;
            margin: 1em;
            font-size: 12pt;
        }

        .el-input {
            font-size: 11pt;
        }

        .el-input .el-input__inner {
            height: 40px;
        }

        .el-button {
            padding: 9pt 11pt;
            font-size: 12pt;
            border-radius: 4pt;
        }
    </style>
</head>
<body>
<div id="app" class="v__addAnimationBg" style="margin: 0">
    <div class="pad" id="login_top" style="height: 80px;padding-top: 15px">
        <span style="font-size: 20pt;"><img style="vertical-align: middle;"
                                            src="${ctx}/image/logo_icon.png"/> 管理系统 - 欢迎登录 </span>
    </div>
    <div class="pad" id="login_main"
         style="position:relative;height: 460px;border: 1px solid #999;background:url(${ctx}/image/login_bg.jpg) no-repeat center top #2E71C2;">
        <div style="width: 350px; height: 320px; float:right; position:relative; top:70px;">
            <el-tabs type="border-card" style="height: 100%">
                <el-tab-pane label="账户登录">
                    <el-input size="large" v-model="username" placeholder="用户名" :value="username"
                              style="margin: 20px auto;">
                        <template slot="prepend"><i class="fa fa-user"></i></template>
                    </el-input>
                    <el-input size="large" v-model="password" placeholder="密码" type="password" :value="password">
                        <template slot="prepend"><i class="fa fa-lock"></i></template>
                    </el-input>
                    <el-button @click="tologin" size="large" type="danger" style="margin-top:30px; width: 100%">登 录
                    </el-button>
                </el-tab-pane>
                <%--<el-tab-pane label="扫码登录">扫码登录</el-tab-pane>--%>
            </el-tabs>
        </div>
    </div>
    <div class="pad" id="login_bottom"><span style="float:left"><a href="#">技术支持</a></span><span style="float:right">系统研发：智仁团队</span>
    </div>
</div>
</body>
<script>
    Vue.http.options.emulateJSON = true;
    var vo = new Vue({
        el: "#app",
        data: function () {
            return {
                username: "",
                password: ""
            }
        },
        methods: {
            tologin: function () {
                this.$http.post("loginAction/login", {username: this.username, user_pwd: this.password})
                        .then(function (result) {
                            var rs = result.bodyText;
                            if (rs.indexOf("error") < 0) {
                                //store.set("token", token.guid);
                                this.loginok();
                            } else {
                                //alert("报错:" + token);
                                alert("用户名或密码不正确，请重新输入！");
                                //store.remove("token");
                            }
                        }, function (error) {
                            console.info("error", error);
                        });
            },
            loginok: function () {
            	//var token = store.get("token");
                // if (token) {
                    window.location = "main";//?token=" + token;
                // }
            }
        }
    });

    Hp.createNew(vo);
</script>
</html>
