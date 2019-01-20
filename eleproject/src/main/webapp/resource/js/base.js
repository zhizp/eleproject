/*
 * 2019-1-7
 */
/**
 * 先隐藏body，待Vue初始化完成后再显示body
 */
//document.body.hidden = true;

/**
 * 启用Vue.http的JSON数据格式提交
 */
Vue.http.options.emulateJSON = true;

/**
 * 注册Vue.http过滤器，在请求头中添加token
 */
Vue.http.interceptors.push(function (request, next) {
    // modify headers
    var token = store.get("token");
    request.headers.set('hecz-token', token);//自定义头中不可有下划线，否则nginx会把带下划线的忽略掉
    //console.info("=====add token to headers=====", token);
    next();
});

// Hp : helper
var Hp = {
    toString: function (toString) {
        if (toString)
            toString();
    },
    resizeWin: function (handle, vo) {
        if (handle) {
            //注册外部方法
            window.onresize = function (evt) {
                handle(evt);
            }
        } else {
            window.onresize = function () {
                var t = document.getElementsByClassName("v__tableDock");//获取包含表格控件的行容器元素
                if (t && t.length > 0) {
                    var h = t[0].offsetHeight;//得到行容器的高度
                    vo.v__tableDockHeight = h;//将高度赋值给表格控件高度绑定的数据
                }
            }
        }
        window.onresize();
    },
    mountedShow: function (vo) {//Vue对象挂载完成后显示body（body默认隐藏）
        vo.$nextTick(function () {
            document.body.style.visibility = "visible";
            //vo.$loading() //显示加载菊花
            //vo.$loading().close() //关闭菊花
        });
    },
    addMenuBg: function (classname) {
        var t = document.getElementsByClassName(classname);
        if (t && t.length > 0) {
            var m = t[0];
            var bg = document.createElement("div");
            m.appendChild(bg);
            bg.outerHTML = '<div class="v__menuBg"></div>';
        }
    },
    addAnimationBg: function (classname) {
        var t = document.getElementsByClassName(classname);
        var d;
        if (t && t.length > 0) {
            d = t[0];
        } else {
            if (vo && vo.$el)
                d = vo.$el;
        }

        if (d) {
            var bg = document.createElement("svg");
            d.appendChild(bg);
            bg.outerHTML = '<svg class="v__animationBg" jsname="BUfzDd" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 810" preserveAspectRatio="xMinYMin slice" aria-hidden="true" x="152.638"><path fill="#efefee" d="M592.66 0c-15 64.092-30.7 125.285-46.598 183.777C634.056 325.56 748.348 550.932 819.642 809.5h419.672C1184.518 593.727 1083.124 290.064 902.637 0H592.66z"></path><path fill="#f6f6f6" d="M545.962 183.777c-53.796 196.576-111.592 361.156-163.49 490.74 11.7 44.494 22.8 89.49 33.1 134.883h404.07c-71.294-258.468-185.586-483.84-273.68-625.623z"></path><path fill="#f7f7f7" d="M153.89 0c74.094 180.678 161.088 417.448 228.483 674.517C449.67 506.337 527.063 279.465 592.56 0H153.89z"></path><path fill="#fbfbfc" d="M153.89 0H0v809.5h415.57C345.477 500.938 240.884 211.874 153.89 0z"></path><path fill="#ebebec" d="M1144.22 501.538c52.596-134.583 101.492-290.964 134.09-463.343 1.2-6.1 2.3-12.298 3.4-18.497 0-.2.1-.4.1-.6 1.1-6.3 2.3-12.7 3.4-19.098H902.536c105.293 169.28 183.688 343.158 241.684 501.638v-.1z"></path><path fill="#e1e1e1" d="M1285.31 0c-2.2 12.798-4.5 25.597-6.9 38.195C1321.507 86.39 1379.603 158.98 1440 257.168V0h-154.69z"></path><path fill="#e7e7e7" d="M1278.31,38.196C1245.81,209.874 1197.22,365.556 1144.82,499.838L1144.82,503.638C1185.82,615.924 1216.41,720.211 1239.11,809.6L1439.7,810L1439.7,256.768C1379.4,158.78 1321.41,86.288 1278.31,38.195L1278.31,38.196z"></path></svg>';
        }
    },
    //---------ElementUI的弹出框弹到主窗体--------------//
    showDlg: function (dlg) {
        // VIP:此方法仅支持ElementUI库的dialog组件
        if (window.parent && window.parent.vm) {
            var v = window.parent.vm;
            v.dlg = dlg;//dlg引入到主页面，便于调试

            v.$el.appendChild(dlg.$el);//将DOM移到当前DOM树（同一个DOM对象只能存在于一个DOM树中）

            // // 将下拉列表DOM移到当前DOM树
            // var sls = dlg.$el.getElementsByClassName("el-select");
            // Vue.nextTick(function(){
            //     console.info(dlg.$el,sls);
            //     for(var i=0; i<sls.length; i++){
            //         console.info(sls[i]["__vue__"]);
            //         var e = sls[i]["__vue__"].popperElm;
            //         v.vo = sls[i];
            //         //v.$el.appendChild(e);
            //     }
            // });

            // 让模态窗可以拖动(监控dlg__header的拖拽)
            dlg.$el.firstChild.firstChild.draggable = true;
            dlg.$el.firstChild.firstChild.style.cursor = "move";

            // 记录开始拖动的位置
            dlg.$el.firstChild.firstChild.ondragstart = function (evt) {
                this.dlgDragStartX = evt.x;
                this.dlgDragStartY = evt.y;
            }
            // 拖动结束，计算位移
            dlg.$el.firstChild.firstChild.ondragend = function (evt) {
                var mo = evt.srcElement;
                var mx = evt.x - this.dlgDragStartX;
                var my = evt.y - this.dlgDragStartY;
                mo.parentElement.style.left = (mo.parentElement.offsetLeft + mx) + "px";
                mo.parentElement.style.top = (mo.parentElement.offsetTop + my) + "px";
            }
        }

        dlg.visible = true;
    },
    $msgbox: function (t, i) {
        if (window.parent && window.parent.vm) {
            var v = window.parent.vm;
            v.$msgbox(t, i);
        }
    },
    $alert: function (e, t, i) {
        if (window.parent && window.parent.vm) {
            var v = window.parent.vm;
            v.$alert(e, t, i);
        }
    },
    $confirm: function (e, t, i) {
        if (window.parent && window.parent.vm) {
            var v = window.parent.vm;
            v.$confirm(e, t, i);
        }
    },
    $prompt: function (e, t, i) {
        if (window.parent && window.parent.vm) {
            var v = window.parent.vm;
            v.$prompt(e, t, i);
        }
    },
    moveElm: function (elm) {
        if (window.parent && window.parent.vm) {
            var v = window.parent.vm;
            console.info(elm);
            v.$el.appendChild(elm);
        }
    },
    //---------------END--------------//
    createNew: function (vo) {
        var ohp = {};
        //注册vue对象相关方法，可用于覆盖默认实现
        Hp.mountedShow(vo);
        Hp.resizeWin(vo.v__resizeWin, vo);
        Hp.toString(vo.v__toString);
        Hp.addMenuBg("el-row");
        // Hp.addAnimationBg("v__addAnimationBg");

        return ohp;
    }
}


