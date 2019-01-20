<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/resource/static/static.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=2.0, minimum-scale=0.5">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <title>欢迎登录</title>
    <link rel="stylesheet" href="${ctx}/resource/common/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}/resource/common/element.css"/>
    <link rel="stylesheet" href="${ctx}/resource/css/base.css"/>
    <script src="${ctx}/resource/common/vue.js"></script>
    <script src="${ctx}/resource/common/vue-resource.min.js"></script>
    <script src="${ctx}/resource/common/element.js"></script>
    <script src="${ctx}/resource/common/store.modern.min.js"></script>
    <script src="${ctx}/resource/js/base.js"></script>
    <link rel="stylesheet" href="${ctx}/resource/layui/css/layui.css"/>
    <script src="${ctx}/resource/layui/layui.all.js"></script>

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
                                            src="${ctx}/resource/image/logo_icon.png"/> 管理系统 - 欢迎登录 </span>
    </div>
    <div class="pad" id="login_main"
         style="position:relative;height: 460px;border: 1px solid #999;background:url(${ctx}/resource/image/login_bg.jpg) no-repeat center top #2E71C2;">
        <div style="width: 350px; height: 320px; float:right; position:relative; top:70px;">
            <el-tabs type="border-card" style="height: 100%">
                <el-tab-pane label="账户登录">
                    <el-form :model="form" :rules="rules" ref="form" >
		                    	<el-form-item  prop="username">
			                    	<el-input size="large" v-model="form.login_name" placeholder="用户名" auto-complete="off" style="margin: 20px auto;margin-bottom:0px;">
			                        	<template slot="prepend"><i class="fa fa-user"></i></template>
			                        </el-input>
		                        </el-form-item>
		                        <el-form-item  prop="user_pwd">
				                    <el-input size="large" v-model="form.user_pwd" placeholder="密码" type="password" auto-complete="off" style="margin-top:20px;">
				                        <template slot="prepend"><i class="fa fa-lock"></i></template>
				                    </el-input>
			                    </el-form-item>
                    </el-form>
                    <el-button @click="tologin('form')"  size="large" type="danger" style="margin-top:30px; width: 100%">登 录
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
                form: {
                	login_name: '',
                	user_pwd: ''
                },
                rules: {
                	login_name: [ { required:true,message: '请输入用户登录名'}],
                	user_pwd: [ { required:true,message: '请输入密码'}]
                }
            }
        },
        methods: {
        	tologin: function (form) {
                        vo.$refs[form].validate(function(valid) {
                            if (valid) {
                            	var formData = JSON.stringify(vo.form);
                		        var url="${ctx}/login";
                                vo.$http.post(url,formData ).then(function(res){
                                	if(res.body.success){
                                        vo.$message({
                                            message: '登录成功！',
                                            duration:1000,
                                            type: 'success',
                                            onClose:function(){
                                                location.href='${ctx}/main';//?token=" + token;
                                            }
                                        });
                                    }else{
                                        vo.$message({
                                            message: '用户名或密码不正确，请重新输入！',
                                            duration:1000,
                                            type: 'error'
                                        });
                                    }
                                });
                            } else {
                                return false;
                            }
                        });
            },
        }
    });

    Hp.createNew(vo);
</script>
</html>
