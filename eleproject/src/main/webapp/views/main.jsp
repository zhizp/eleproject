<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<jsp:include page="/static/static.jsp"></jsp:include>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>信息中心内部ERP</title>
    <%--<link rel="stylesheet" href="${ctx}/common/bootstrap-3.3.7.min.css">--%>
    <link rel="stylesheet" href="${ctx}/common/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/common/element.css">
    <link rel="stylesheet" href="${ctx}/css/base.css">
    <script src="${ctx}/common/vue.js"></script>
    <script src="${ctx}/common/vue-resource.min.js"></script>
    <script src="${ctx}/common/element.js"></script>
    <script src="${ctx}/common/array.js"></script>
    <script src="${ctx}/common/store.modern.min.js"></script>
    <script src="${ctx}/common/jquery-1.12.4.min.js"></script>
    <script src="${ctx}/js/base.js"></script>
    <script src="${ctx}/common/store.modern.min.js"></script>
    <style>
        body {
            padding: 0;
            margin: 0;
        }

        .el-tabs {
            height: 100%;
            background: url(${ctx}/image/blank_bg.jpg);
            background-size: cover;
            border: none;
        }

        .el-tabs__content {
            position: absolute;
            top: 42px;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 0;
        }

        .el-tabs--border-card > .el-tabs__header {
            border-bottom: none;
        }

        .el-tabs--border-card > .el-tabs__content {
            padding: 0;
        }

        .el-tabs .el-tab-pane {
            height: 100%;
            background-color: #fff;
        }

        .el-tab-pane > iframe {
            width: 100%;
            height: 100%;
            border: 0;
        }

        /* 可以设置不同的进入和离开动画 */
        /* 设置持续时间和动画函数 */
        .slide-fade-enter-active, .slide-fade-leave-active {
            transition: all .2s ease;
        }

        /* .slide-fade-enter, .slide-fade-leave-active {
            transform: translateX(-200px);
            opacity: 0;
        } */

        .imgcsss {
            height: 15px;
            width: 15px;
            margin: -3px 10px 0px 0px;
        }

        .app_setting {
            background-color: #0171c3;
            position: absolute;
            bottom: 0;
            text-align: center;
        }

        .app_setting .el-button {
            font-size: 11pt;
            color: #fff;
        }
        .el-submenu__title i{
        	color:#fff;
        }
    </style>
</head>

<body>
<div id="app_main">
    <el-row style="line-height: 60px; background-color: #0171C3">
        <el-col :span="12"
                style="text-align: left; color:#fff; font-family: 'Microsoft YaHei UI'; font-size: 18pt; text-indent: 0.5em;position: relative; z-index: 999;text-shadow: 2px 3px 2px #333;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
            <img src="${ctx}/image/logo_icon.png">
            <span>河北财政信息系统一体化平台</span>
            <span style="padding-left:0.5em;font-size:17pt;font-weight:bold">信息中心ERP</span>
        </el-col>
        <el-col :span="12" style="text-align: right">
            <img src="${ctx}/image/logo_bg.png" style="position: absolute; left: 360px;">
            <el-button-group style="margin-right: 1em">
                <el-button type="text" title="" style="color:#fff;font-size:12pt" @click="updatePwd">
                    <i class="fa fa-key" style="font-size:14pt"></i> 修改密码
                </el-button>
                <el-button type="text" title="退出" style="color:#fff;font-size:12pt" @click="handleLogout">
                    <i class="fa fa-times-circle" style="font-size:14pt"></i> 退出
                </el-button>
            </el-button-group>
        </el-col>
    </el-row>
    <el-row style="position: fixed; top:60px; bottom: 0; width: 100%">
        <transition name="slide-fade">
            <el-col id="app_menu" v-show="showMenu" :span="4"
                    style="position: relative;height: 100%; background-color: #e4e8f1">
                <div class="app_userinfo" style="height:42px;background-color:rgb(1, 113, 195);color:#fff;">
                    <i class="fa fa-user-circle" style="font-size: 24pt;padding-left: 20px;padding-right: 14px;"> </i>
                    <ul style="display: inline-block">
                        <li id="username">{{userinfo.username}}</li>
                        <li>登录日期：{{userinfo.date}}</li>
                    </ul>
                </div>
                <!-- 左侧菜单begin -->
                <div style="position: absolute; width: 100%; top:42px; bottom:28px/*--控件有2px的下边--*/; overflow: auto;">
                    <el-menu :default-openeds="menus.opens" @open="handleOpen" @close="handleClose"
                             :collapse="isCollapse">
                        <!--无子菜单  -->
                        <el-menu-item :key="item.key" v-for="item in menus.items" :index="item.key"
                                      :uri="item.uri?item.uri:'#'" @click="handleTabAdd">
                            <i class="fa fa-file-text"></i> {{item.text}}
                        </el-menu-item>
                        <!--  有子菜单-->
                        <el-submenu :key="sub.key" v-for="sub in menus.subs" :index="sub.key">
                            <template slot="title">
                                <i class="fa fa-file-text"></i>
                                <span slot="title"> {{sub.text}}</span>
                            </template>
                            <el-menu-item :key="item.key" v-for="item in sub.items" :index="item.key"
                                          :uri="item.uri?item.uri:'#'" @click="handleTabAdd">
                                <i class="fa fa-file-text"></i> {{item.text}}
                            </el-menu-item>
                            <el-submenu :key="sub_1.key" v-for="sub_1 in sub.subs" :index="sub_1.key">
                                <template slot="title"><i class="fa fa-file-text"></i> {{sub_1.text}}
                                </template>
                                <el-menu-item :key="item.key" v-for="item in sub_1.items" :index="item.key"
                                              :uri="item.uri?item.uri:'#'" @click="handleTabAdd">
                                   <i class="fa fa-file-text"></i> {{item.text}}
                                </el-menu-item>
                            </el-submenu>
                        </el-submenu>

                    </el-menu>
                </div>
                <!-- 左侧菜单end -->
                <!-- <el-col class="app_setting" :span="24">
                    <el-button-group>
                        <el-button type="text" title="设置" class="fa fa-cog"> 设置
                        </el-button>
                        <el-button type="text" title="通知" class="fa fa-bell"> 通知
                        </el-button>
                        <el-button type="text" title="消息" class="fa fa-commenting"> 消息
                        </el-button>
                    </el-button-group>
                </el-col> -->
            </el-col>
        </transition>

        <transition name="slide-fade">
            <el-col id="app_main" :span="mainSpan" style="height: 100%; position: relative;">
                <el-tabs type="border-card" v-model="activeName" :active-name="activeTab" @tab-remove="handleTabRemove"
                         closable>
                    <el-tab-pane v-for="tab in tabs" :key="tab.key" :name="tab.key" :label="tab.text + tab.key"
                                 v-if="tab.open">
                        <span slot="label" v-on:mousedown="handleTabClick($event,tab.key)"><i
                                class="el-icon-document"></i> {{tab.text}}</span>
                        <iframe :src="tab.uri"></iframe>
                    </el-tab-pane>
                </el-tabs>
            </el-col>
        </transition>
    </el-row>
    <el-row style="display:none; position:fixed; width:100%; height:22px; bottom:0; background-color:#e1e6ed; text-align:center; box-shadow:inset 0 2px 2px #ccc;">
        <el-col :span="24">底栏</el-col>
    </el-row>
    <div id="dlg"></div>
</div>
</body>

<script>
    var vm = new Vue({
        el: "#app_main",
        data: function () {
            return {
                init: null,
                userinfo: {
                    username: "",
                    userdept: "",
                    date: ""
                },
                token: store.get("token"),
                showMenu: true,
                isCollapse: false,
                mainSpan: 20,
                imgpath: "${ctx}/upload/menu/",
                years: [{
                    value: '2017',
                    label: '2017年'
                }, {
                    value: '2018',
                    label: '2018年'
                }, {
                    value: '2019',
                    label: '2019年'
                }],
                theYear: '2017',
                activeTab: "help1",
                activeName: "help1",
                tabs: [
                    //{key: "help1", text: "关于常用组件", open: true, uri: "help"}
                ],
                menus: {
                    items: [], subs: [], opens: []
                },
                dlg: null,//调试用
                vo: null//调试用
            }
        },
        mounted: function () {
            this.$nextTick(function () {
                this.storeToken();
                // 保证this.$el已经插入文档
                this.getSysUrl();//打开默认子系统
                //打开待办页面
                var key = 'ebe8412a-cf5e-4a8a-9530-af89995f9e93';
                this.tabs.push({key: key, text: '待办', open: true, closable:false,uri: 'job/waitlist' + "?token=" + this.token});
                this.activeTab = key;
                this.activeName = key;
            })
        },
        methods: {
            getQueryString: function (name) {
                // 从url获取参数
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]);
                return null;
            },
            storeToken: function () {
                var tk = this.getQueryString("token");
                store.set("token", tk);
                this.token = store.get("token");
            },
            getSysUrl: function (sys) {
                this.$http.get("getSysUrl", {
                    params: {
                        "token": this.token,
                        "sys": sys
                    }
                }).then(function (res) {
                    //console.info(res);
                    this.bindTreeData(res);
                });
            },
            bindTreeData: function (res) {
                this.init = res.body;
                this.init.menus.subs.sort(function(a,b){return a.idx*1<b.idx*1?-1:a.idx*1>b.idx*1?1:0});
                this.menus = this.init.menus;
                this.userinfo.username = this.init.user.USERNAME;
                this.userinfo.userdept = this.init.user.ORGNAME;
                this.userinfo.date = this.init.info.date;
            },
            openSys: function () {
                this.getSysUrl("hbzdk");//手动切换子系统
            },
            handleOpen: function (key, keyPath) {
                //console.log(key, keyPath);
            },
            handleClose: function (key, keyPath) {
                //console.log(key, keyPath);
            },
            handleTabAdd: function (evt) {
                // console.info(evt);
                var key = evt.$vnode.key;
                var text = evt.$el.innerText;
                var uri = evt.$el.attributes["uri"].value;
                var arg = "?token=" + this.token;
                var has = this.tabs.some(function (p) {
                    if (p.key === key) return true
                });
                if (!has) {
                    if (this.tabs.length < 10) {
                        this.tabs.push({key: key, text: text, open: true, uri: uri + arg});
                        this.activeTab = key;
                        this.activeName = key;
                    } else {
                        this.$message.warning("已达最大标签数！");
                    }
                } else {
                    //this.$message.info("已打开此功能页！");
                    this.activeTab = key;
                    this.activeName = key;
                }
            },
            handleTabRemove: function (name) {
                //console.info(name);
                var idx = this.tabs.findIndex(function (p) {
                    if (p.key === name) return p
                });
                this.tabs.splice(idx, 1);
                if (idx > 0) {
                    var nextTab = this.tabs[idx - 1 > 0 ? idx - 1 : 0];
                    this.activeTab = nextTab.key;
                }

            },
            handleTabClick: function (evt, tabkey) {
                //鼠标中键
                if (evt.button == 1 || evt.buttons == 4) {
                    this.handleTabRemove(tabkey);
                }
            },
            handleShowMenu: function () {
                this.showMenu = !this.showMenu
                if (this.showMenu) {
                    vm.mainSpan = 20;
                } else {
                    setTimeout("vm.mainSpan=24", 200);
                }
            },
            handleShowMsg: function (evt) {
                // console.info(evt.$vnode.key, evt);
                if (evt.$vnode.key == "m22") {
                    this.$message({
                        message: '恭喜你，这是一条成功消息',
                        type: 'success'
                    });
                } else {
                    this.$notify({
                        title: '成功',
                        message: '这是一条成功的提示消息',
                        type: 'success'
                    });
                }
            },
            handleLogout: function () {
                var t = store.get("token");
                store.remove("token");
                //self.location.href = "api/logout?token=" + t;

                this.$http.get("api/logout", {
                    params: {
                        "token": t
                    }
                }).then(function (res) {
                    //console.info(res);
                    self.location.href = "login";
                });
            },
            handleCollapseMenu: function () {
                this.isCollapse = !this.isCollapse
            },
            updatePwd: function(){//修改密码
                var key = 'b4f29de8-f591-46e1-ae76-03fb92dc4d58';
                var has = this.tabs.some(function (p) {
                    if (p.key === key) return true
                });
               if (!has) {
                    if (this.tabs.length < 10) {
                        this.tabs.push({key: key, text: '修改密码', open: true, closable:false,uri: 'sysManage/updatePwd' + "?token=" + this.token});
                        this.activeTab = key;
                        this.activeName = key;
                    } else {
                        this.$message.warning("已达最大标签数！");
                    }
                } else {
                    this.activeTab = key;
                    this.activeName = key;
                } 

            }
        }
    })
    Hp.createNew(vm);
</script>

</html>