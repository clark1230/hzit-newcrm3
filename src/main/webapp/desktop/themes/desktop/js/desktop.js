/*
 * Windows WebOS
 * WEB 桌面风格，基于Layui-打造的Windows WebOS桌面风格，实现了右键、开始菜单、返回桌面等一些列功能。窗口全部由 layer 完成
 * http://webos.90zs.net/
 * Copyright 2016-2017, SMALL,1531982850
 * The 90zs.net
 * http://www.90zs.net/
 * Released on: 12, 2016
 */

!function () {
    layui.form();
    var b = layui.jquery, d = layui.layer, l = layui.laytpl;
    b("#loading").hide().remove();
    var c = {
        setting: function (a) {
            c.hidemenu();
            d.alert("\u5bf9\u4e0d\u8d77\uff0c\u6211\u8fd8\u4e0d\u80fd\u6ee1\u8db3\u4f60", {
                icon: 5,
                title: "\u7cfb\u7edf\u91cd\u8981\u63d0\u793a"
            })
        }, theme: function (a) {
            c.hidemenu();
            b(this).data({
                url: "themes",
                isicon: 0,
                icon: "&#xe638;",
                height: 400,
                width: 460,
                iconbg: "#51555e",
                title: "\u80cc\u666f\u8bbe\u7f6e"
            });
            c.appopen(b(this))
        }, users: function (a) {
            c.hidemenu();
            d.msg('个人信息!!!');
        }, loginout: function (a) {
            c.hidemenu();
            d.alert("\u6ce8\u9500\u767b\u5f55")
        }, technicalsupport: function (a) {
            c.hidemenu();
            d.alert("\u52a0QQ\u7fa4\u554a\uff08601178086\uff09\uff0c\u6709\u6e90\u7801\uff0c\u6709\u840c\u59b9\u5b50", {
                icon: 1,
                title: "\u6280\u672f\u652f\u6301"
            })
        }, lockscreen: function (a) {
            d.open({
                type: 1,
                title: !1,
                closeBtn: !1,
                area: "300px;",
                shade: .8,
                id: "LAY_layuipro",
                resize: !1,
                btn: ["\u89e3\u9501"],
                btnAlign: "c",
                moveType: 1,
                content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">\u597d\u4e86\uff0c\u5c01\u5370\u89e3\u9664</div>',
                success: function (a) {
                }
            });
            c.hidemenu()
        },refreshwindow:function(a){
                c.hidemenu();
                alert('刷新');
                window.location.reload();
        }, closeall: function (a) {
            a = b(".taskbar-app").length;
            c.hidemenu();
            1 > a || d.alert("\u786e\u5b9a\u5173\u95ed\u6240\u6709\u7a97\u53e3\uff1f", {
                icon: 0,
                btn: ["\u786e\u5b9a", "\u53d6\u6d88"],
                zIndex: parseInt(d.zIndex + 1),
                yes: function (a, e) {
                    b(document).find(".taskbar-app").remove();
                    d.closeAll("iframe");
                    d.close(a)
                },
                end: function () {
                }
            })
        }, showdesktop: function (a) {
            c.hidemenu();
            b(document).find(".layui-layer .layui-layer-min").click();
            b(document).find(".taskbar-app").removeClass("taskbar-app-on")
        },
        hidemenu: function (a) {
            b(".desktop-menu").hide()
        }, hidopeningemenu: function () {
            b(".opening-menu").removeClass("opening-menu-on")
        }, openingmenu: function (a) {
            b("#opening-menu").toggleClass("opening-menu-on").off("mousedown", c.stope).on("mousedown", c.stope);
            b(document).off("mousedown", c.hidopeningemenu).on("mousedown", c.hidopeningemenu);
            b(window).off("resize", c.hidopeningemenu).on("resize", c.hidopeningemenu);
            a.off("mousedown", c.stope).on("mousedown", c.stope)
        }, hide: function () {
            d.closeAll("tips")
        }, pattern: function (a) {
            var b =
                new Date, e = {
                "M+": b.getMonth() + 1,
                "d+": b.getDate(),
                "h+": 0 == b.getHours() % 12 ? 12 : b.getHours() % 12,
                "H+": b.getHours(),
                "m+": b.getMinutes(),
                "s+": b.getSeconds(),
                "q+": Math.floor((b.getMonth() + 3) / 3),
                S: b.getMilliseconds()
            }, c = {0: "\u65e5", 1: "\u4e00", 2: "\u4e8c", 3: "\u4e09", 4: "\u56db", 5: "\u4e94", 6: "\u516d"};
            /(y+)/.test(a) && (a = a.replace(RegExp.$1, (b.getFullYear() + "").substr(4 - RegExp.$1.length)));
            /(E+)/.test(a) && (a = a.replace(RegExp.$1, (1 < RegExp.$1.length ? 2 < RegExp.$1.length ? "\u661f\u671f" : "\u5468" : "") + c[b.getDay() + ""]));
            for (var d in e)(new RegExp("(" + d + ")")).test(a) && (a = a.replace(RegExp.$1, 1 == RegExp.$1.length ? e[d] : ("00" + e[d]).substr(("" + e[d]).length)));
            return a
        }, refreshtime: function () {
            b(".taskbar-time").attr("title", c.pattern("yyyy\u5e74MM\u6708dd\u65e5 EEE"));
            b("#laydate-hs").text(c.pattern("HH:mm"));
            b("#laydate-ymd").text(c.pattern("yyyy/MM/dd"))
        }, appopen: function (a) {
            var c = !0, e = a.data();
            b(document).find(".taskbar-app").each(function (d, f) {
                b(f).attr("title") == e.title && (a.removeClass("disabled"), b(f).click(), c = !1)
            });
            if (c) {
                var f = b(".taskbar-app").length + 1, h = parseInt((layui.jquery(".desktop-taskbar").width() - 160) / 110);
                if (f > h) d.alert("\u8bf7\u5148\u5173\u95ed\u4e00\u4e9b\u7a97\u53e3\uff01", {
                    title: "\u5b98\u4eba\u4f11\u606f\u4e0b\uff1f",
                    icon: 2,
                    zIndex: d.zIndex + 1
                }, function (b) {
                    a.removeClass("disabled");
                    d.close(b)
                }); else {
                    var f = e.width ? e.width : .8 * b(".desktop-container").width(), h = e.height ? e.height : .9 * b(".desktop-container").height(), g = "", k = d.open({
                        type: 2,
                        title: [e.title, "background-color:#c9c9c9;color:#fff"],
                        shadeClose: !0,
                        shade: !1,
                        maxmin: !0,
                        area: [f + "px", h + "px"],
                        content: e.url,
                        zIndex: d.zIndex,
                        skin: "desktop-win-app",
                        success: function (b, c) {
                            a.removeClass("disabled");
                            d.setTop(b);
                            b.find(".layui-refreswind").is(":visible") || b.find(".layui-layer-setwin").prepend('<a class="layui-icon small-click layui-refreswind" data-type="refreshWind" data-id="' + c + '">&#x1002;</a>')
                        },
                        min: function (a, c) {
                            b(a).hide();
                            b("#" + g).removeClass("taskbar-app-on");
                            var e = [];
                            b(document).find(".layui-layer-iframe:visible").each(function (a, c) {
                                e.push(b(c).css("z-index"))
                            });
                            if (1 > e.length)return !1;
                            var d = e.sort().pop();
                            b(document).find(".layui-layer-iframe:visible").each(function (a, c) {
                                if (b(c).css("z-index") == d)return b("#taskbar-" + b(c).attr("id")).addClass("taskbar-app-on"), !1
                            });
                            return !1
                        },
                        full: function (b, a) {
                        },
                        restore: function (b, a) {
                        },
                        moveEnd: function () {
                            b("#" + g).addClass("taskbar-app-on").siblings().removeClass("taskbar-app-on")
                        },
                        cancel: function (a) {
                            var c = layui.data("desktop-app")["desktop-app-" + a];
                            layui.each(c, function (b, a) {
                                d.close(a)
                            });
                            layui.data("desktop-app", {
                                key: "desktop-app-" +
                                a, remove: !0
                            });
                            b("#" + g).remove()
                        },
                        end: function () {
                            a.removeClass("disabled")
                        }
                    }), g = "taskbar-layui-layer" + k, f = "", f = e.isicon ? "" + ('<div class="layui-inline layui-elip taskbar-app taskbar-app-on" title="' + e.title + '" id="' + g + '"><i class="layui-icon" style=" background-color:' + e.iconbg + '">' + e.icon + '</i><span class="desktop-title layui-elip">' + e.title + "</span></div>") : "" + ('<div class="layui-inline layui-elip taskbar-app taskbar-app-on" title="' + e.title + '" id="' + g + '"><span class="desktop-title layui-elip">' +
                        e.title + "</span></div>");
                    b("#" + g).is(":visible") || (b(".desktop-taskbar-app-list").append(f), b("#" + g).on("click", function () {
                        var a = b(this);
                        a.hasClass("taskbar-app-on") ? b("#layui-layer" + k).find(".layui-layer-setwin .layui-layer-min").click() : (a.addClass("taskbar-app-on").siblings().removeClass("taskbar-app-on"), b("#layui-layer" + k).show(), d.zIndex = parseInt(d.zIndex + 1), d.style(k, {zIndex: d.zIndex}))
                    }).siblings().removeClass("taskbar-app-on"))
                }
            }
        }, stope: function (a) {
            a = a || window.event;
            a.stopPropagation ? a.stopPropagation() :
                a.cancelBubble = !0
        }, arrange: function (a) {
            a = b(".swiper-slide-active").index();
            a = b(".desktopContainer:eq(" + ("" == a || void 0 == a ? 0 : a) + ")");
            var c = b(".desktopContainer"), e = 0, d = 0, h = 96, g = 96, k = 0, k = c.height() - 40;
            c.width();
            a.find(".desktop-app").each(function (a, c) {
                b(c).css("top", d + "px");
                b(c).css("left", e + "px");
                g = b(c).height();
                h = b(c).width();
                d = d + g + 10 + 10;
                d >= k - 65 && (d = 0, e = e + h + 10)
            })
        }, refreshWind: function (a) {
            a = a.data("id");
            url = b("#layui-layer-iframe" + a).attr("src");
            d.iframeSrc(a, url)
        }, init: function () {
            l('{{# layui.each(d.menu, function(index, menuitem){ if(index>=3)return false;}}<div class="swiper-slide"><div class="desktopContainer"  data-menuid="{{menuitem.menuid}}" data-name="{{menuitem.name}}" >{{# layui.each(menuitem.app, function(index, app){}}<div class="desktop-app" data-id="{{d.apps[app].appid}} " data-title="{{d.apps[app].name}}" data-url="{{d.apps[app].url}}" data-icon="{{d.apps[app].icon}}" data-iconbg="{{d.apps[app].iconbg}}"  data-isicon="{{d.apps[app].isicon}}" data-height="{{d.apps[app].height}}" data-width="{{d.apps[app].width}}" data-fid="{{app}}"><i class="layui-icon" style="background-color:{{d.apps[app].iconbg}}">{{d.apps[app].icon}}</i><span class="desktop-title layui-elip">{{d.apps[app].name}}</span></div>{{# });}}</div></div>{{# }); }} ').render(desktpData,
                function (a) {
                    b(".swiper-wrapper").html(a)
                });
            l('{{# layui.each(d.menu[3].app, function(index, app){}}<div class="desktop-app" data-id="{{d.apps[app].appid}} " data-title="{{d.apps[app].name}}" data-url="{{d.apps[app].url}}" data-icon="{{d.apps[app].icon}}" data-iconbg="{{d.apps[app].iconbg}}"  data-isicon="{{d.apps[app].isicon}}" data-height="{{d.apps[app].height}}" data-width="{{d.apps[app].width}}" data-fid="{{app}}"><i class="layui-icon" style="background-color:{{d.apps[app].iconbg}}">{{d.apps[app].icon}}</i><span class="desktop-title layui-elip">{{d.apps[app].name}}</span></div>{{# });}}').render(desktpData,
                function (a) {
                    b(".opening-menu-app-list").html(a)
                });
            b(".desktop-container").css("height", b(window).height() - 30);
            d.open({
                type: 1,
                title: "\u4fbf\u7b7e",
                area: "250px",
                skin: "layui-layer-notepaper",
                offset: "rt",
                anim: 6,
                shade: !1,
                content: '<textarea class="layui-textarea notepaper">QQ群：601178086(有源码););2017-1-22:窗体新加刷新按钮;2017-1-11:新加背景图片更改，看菜单；解决了锁屏z-index不够的问题；2016-12-27:重写了js;2016-12-30:修复了右下角时间显示，layim起始位置修复在窗体内；2017-1-5:修复个人资料提示;2:修复任务栏超多提示后点击失效问题；——BY SMALL</textarea>',
                success: function (a, c) {
                    b(a).find(".notepaper").on("change", function () {
                        console.log(b(this).val())
                    })
                }
            });
            new Swiper(".swiper-container",
                {
                    pagination: ".swiper-pagination",
                    simulateTouch: !1,
                    slidesPerView: 1,
                    paginationClickable: !0,
                    spaceBetween: 30,
                    keyboardControl: !0,
                    mousewheelControl: !0,
                    onSlideChangeEnd: function (a) {
                        c.arrange(a.realIndex)
                    }
                });
            b(window).resize(function (a) {
                b(".desktop-container").css("height", b(window).height() - 40);
                b(".desktopContainer").css("height", b(".desktop-container").height());
                c.arrange()
            });
            b(".desktopContainer").sortable({revert: !0});
            b(".desktopContainer").sortable({
                connectToSortable: ".desktopContainer", stop: function (a,
                                                                        b) {
                    c.arrange()
                }
            }).disableSelection();
            c.arrange();
            setInterval(c.refreshtime, 1E3);
            b(document).contextmenu(function () {
                return !1
            });
            b(".desktopContainer").on("contextmenu", function (a) {
                var c = a.clientX;
                a = a.clientY;
                var d = b(".desktop-menu"), f = document.body.clientWidth, h = document.body.clientHeight, c = c + d.width() >= f ? f - d.width() - 15 : c;
                a = a + d.height() >= h - 40 ? h - d.height() - 15 : a;
                d.css({top: a, left: c}).show()
            });
            b(".desktop-app").on("click", function () {
                var a = b(this);
                if (a.hasClass("disabled"))return !1;
                a.addClass("disabled");
                c.appopen(a)
            })
        }
    };
    c.init();
    b("body").on("click", ".small-click", function () {
        var a = b(this), d = a.data("type");
        c[d] ? c[d].call(this, a) : ""
    })
}();