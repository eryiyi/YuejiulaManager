if (!AmCharts)var AmCharts = {
    themes: {},
    maps: {},
    inheriting: {},
    charts: [],
    onReadyArray: [],
    useUTC: !1,
    updateRate: 40,
    uid: 0,
    lang: {},
    translations: {},
    mapTranslations: {},
    windows: {},
    initHandlers: []
};
AmCharts.Class = function (a) {
    var b = function () {
        arguments[0] !== AmCharts.inheriting && (this.events = {}, this.construct.apply(this, arguments))
    };
    a.inherits ? (b.prototype = new a.inherits(AmCharts.inheriting), b.base = a.inherits.prototype, delete a.inherits) : (b.prototype.createEvents = function () {
        for (var a = 0, b = arguments.length; a < b; a++)this.events[arguments[a]] = []
    }, b.prototype.listenTo = function (a, b, c) {
        this.removeListener(a, b, c);
        a.events[b].push({handler: c, scope: this})
    }, b.prototype.addListener = function (a, b, c) {
        this.removeListener(this,
            a, b);
        this.events[a].push({handler: b, scope: c})
    }, b.prototype.removeListener = function (a, b, c) {
        if (a && a.events)for (a = a.events[b], b = a.length - 1; 0 <= b; b--)a[b].handler === c && a.splice(b, 1)
    }, b.prototype.fire = function (a, b) {
        for (var c = this.events[a], g = 0, h = c.length; g < h; g++) {
            var k = c[g];
            k.handler.call(k.scope, b)
        }
    });
    for (var c in a)b.prototype[c] = a[c];
    return b
};
AmCharts.addChart = function (a) {
    AmCharts.charts.push(a)
};
AmCharts.removeChart = function (a) {
    for (var b = AmCharts.charts, c = b.length - 1; 0 <= c; c--)b[c] == a && b.splice(c, 1)
};
AmCharts.isModern = !0;
AmCharts.getIEVersion = function () {
    var a = 0;
    if ("Microsoft Internet Explorer" == navigator.appName) {
        var b = navigator.userAgent, c = /MSIE ([0-9]{1,}[.0-9]{0,})/;
        null != c.exec(b) && (a = parseFloat(RegExp.$1))
    } else"Netscape" == navigator.appName && (b = navigator.userAgent, c = /Trident\/.*rv:([0-9]{1,}[.0-9]{0,})/, null != c.exec(b) && (a = parseFloat(RegExp.$1)));
    return a
};
AmCharts.applyLang = function (a, b) {
    var c = AmCharts.translations;
    b.dayNames = AmCharts.dayNames;
    b.shortDayNames = AmCharts.shortDayNames;
    b.monthNames = AmCharts.monthNames;
    b.shortMonthNames = AmCharts.shortMonthNames;
    c && (c = c[a]) && (AmCharts.lang = c, c.monthNames && (b.dayNames = c.dayNames, b.shortDayNames = c.shortDayNames, b.monthNames = c.monthNames, b.shortMonthNames = c.shortMonthNames))
};
AmCharts.IEversion = AmCharts.getIEVersion();
9 > AmCharts.IEversion && 0 < AmCharts.IEversion && (AmCharts.isModern = !1, AmCharts.isIE = !0);
AmCharts.dx = 0;
AmCharts.dy = 0;
if (document.addEventListener || window.opera)AmCharts.isNN = !0, AmCharts.isIE = !1, AmCharts.dx = .5, AmCharts.dy = .5;
document.attachEvent && (AmCharts.isNN = !1, AmCharts.isIE = !0, AmCharts.isModern || (AmCharts.dx = 0, AmCharts.dy = 0));
window.chrome && (AmCharts.chrome = !0);
AmCharts.handleResize = function () {
    for (var a = AmCharts.charts, b = 0; b < a.length; b++) {
        var c = a[b];
        c && c.div && c.handleResize()
    }
};
AmCharts.handleMouseUp = function (a) {
    for (var b = AmCharts.charts, c = 0; c < b.length; c++) {
        var d = b[c];
        d && d.handleReleaseOutside(a)
    }
};
AmCharts.handleMouseMove = function (a) {
    for (var b = AmCharts.charts, c = 0; c < b.length; c++) {
        var d = b[c];
        d && d.handleMouseMove(a)
    }
};
AmCharts.resetMouseOver = function () {
    for (var a = AmCharts.charts, b = 0; b < a.length; b++) {
        var c = a[b];
        c && (c.mouseIsOver = !1)
    }
};
AmCharts.ready = function (a) {
    AmCharts.onReadyArray.push(a)
};
AmCharts.handleLoad = function () {
    AmCharts.isReady = !0;
    for (var a = AmCharts.onReadyArray, b = 0; b < a.length; b++) {
        var c = a[b];
        isNaN(AmCharts.processDelay) ? c() : setTimeout(c, AmCharts.processDelay * b)
    }
};
AmCharts.addInitHandler = function (a, b) {
    AmCharts.initHandlers.push({method: a, types: b})
};
AmCharts.callInitHandler = function (a) {
    var b = AmCharts.initHandlers;
    if (AmCharts.initHandlers)for (var c = 0; c < b.length; c++) {
        var d = b[c];
        d.types && -1 != d.types.indexOf(a.type) && d.method(a)
    }
};
AmCharts.getUniqueId = function () {
    AmCharts.uid++;
    return "AmChartsEl-" + AmCharts.uid
};
AmCharts.isNN && (document.addEventListener("mousemove", AmCharts.handleMouseMove, !0), window.addEventListener("resize", AmCharts.handleResize, !0), document.addEventListener("mouseup", AmCharts.handleMouseUp, !0), window.addEventListener("load", AmCharts.handleLoad, !0));
AmCharts.isIE && (document.attachEvent("onmousemove", AmCharts.handleMouseMove), window.attachEvent("onresize", AmCharts.handleResize), document.attachEvent("onmouseup", AmCharts.handleMouseUp), window.attachEvent("onload", AmCharts.handleLoad));
AmCharts.clear = function () {
    var a = AmCharts.charts;
    if (a)for (var b = 0; b < a.length; b++)a[b].clear();
    AmCharts.charts = null;
    AmCharts.isNN && (document.removeEventListener("mousemove", AmCharts.handleMouseMove, !0), window.removeEventListener("resize", AmCharts.handleResize, !0), document.removeEventListener("mouseup", AmCharts.handleMouseUp, !0), window.removeEventListener("load", AmCharts.handleLoad, !0));
    AmCharts.isIE && (document.detachEvent("onmousemove", AmCharts.handleMouseMove), window.detachEvent("onresize", AmCharts.handleResize),
        document.detachEvent("onmouseup", AmCharts.handleMouseUp), window.detachEvent("onload", AmCharts.handleLoad))
};
AmCharts.makeChart = function (a, b, c) {
    var d = b.type, e = b.theme;
    AmCharts.isString(e) && (e = AmCharts.themes[e], b.theme = e);
    var f;
    switch (d) {
        case "serial":
            f = new AmCharts.AmSerialChart(e);
            break;
        case "xy":
            f = new AmCharts.AmXYChart(e);
            break;
        case "pie":
            f = new AmCharts.AmPieChart(e);
            break;
        case "radar":
            f = new AmCharts.AmRadarChart(e);
            break;
        case "gauge":
            f = new AmCharts.AmAngularGauge(e);
            break;
        case "funnel":
            f = new AmCharts.AmFunnelChart(e);
            break;
        case "map":
            f = new AmCharts.AmMap(e);
            break;
        case "stock":
            f = new AmCharts.AmStockChart(e)
    }
    AmCharts.extend(f,
        b);
    AmCharts.isReady ? isNaN(c) ? f.write(a) : setTimeout(function () {
        AmCharts.realWrite(f, a)
    }, c) : AmCharts.ready(function () {
        isNaN(c) ? f.write(a) : setTimeout(function () {
            AmCharts.realWrite(f, a)
        }, c)
    });
    return f
};
AmCharts.realWrite = function (a, b) {
    a.write(b)
};
AmCharts.toBoolean = function (a, b) {
    if (void 0 === a)return b;
    switch (String(a).toLowerCase()) {
        case "true":
        case "yes":
        case "1":
            return !0;
        case "false":
        case "no":
        case "0":
        case null:
            return !1;
        default:
            return Boolean(a)
    }
};
AmCharts.removeFromArray = function (a, b) {
    var c;
    for (c = a.length - 1; 0 <= c; c--)a[c] == b && a.splice(c, 1)
};
AmCharts.getDecimals = function (a) {
    var b = 0;
    isNaN(a) || (a = String(a), -1 != a.indexOf("e-") ? b = Number(a.split("-")[1]) : -1 != a.indexOf(".") && (b = a.split(".")[1].length));
    return b
};
AmCharts.wrappedText = function (a, b, c, d, e, f, g, h, k) {
    var l = AmCharts.text(a, b, c, d, e, f, g), m = "\n";
    AmCharts.isModern || (m = "<br>");
    if (10 < k)return l;
    if (l) {
        var n = l.getBBox();
        if (n.width > h) {
            l.remove();
            for (var l = [], p = 0; -1 < (index = b.indexOf(" ", p));)l.push(index), p = index + 1;
            for (var q = Math.round(b.length / 2), r = 1E3, s, p = 0; p < l.length; p++) {
                var w = Math.abs(l[p] - q);
                w < r && (s = l[p], r = w)
            }
            if (isNaN(s)) {
                h = Math.ceil(n.width / h);
                if (0 == k)for (p = 1; p < h; p++)s = Math.round(b.length / h * p), b = b.substr(0, s) + m + b.substr(s);
                return AmCharts.text(a, b,
                    c, d, e, f, g)
            }
            b = b.substr(0, s) + m + b.substr(s + 1);
            return AmCharts.wrappedText(a, b, c, d, e, f, g, h, k + 1)
        }
        return l
    }
};
AmCharts.getStyle = function (a, b) {
    var c = "";
    document.defaultView && document.defaultView.getComputedStyle ? c = document.defaultView.getComputedStyle(a, "").getPropertyValue(b) : a.currentStyle && (b = b.replace(/\-(\w)/g, function (a, b) {
        return b.toUpperCase()
    }), c = a.currentStyle[b]);
    return c
};
AmCharts.removePx = function (a) {
    if (void 0 != a)return Number(a.substring(0, a.length - 2))
};
AmCharts.getURL = function (a, b) {
    if (a)if ("_self" != b && b)if ("_top" == b && window.top)window.top.location.href = a; else if ("_parent" == b && window.parent)window.parent.location.href = a; else if ("_blank" == b)window.open(a); else {
        var c = document.getElementsByName(b)[0];
        c ? c.src = a : (c = AmCharts.windows[b]) ? c.opener && !c.opener.closed ? c.location.href = a : AmCharts.windows[b] = window.open(a) : AmCharts.windows[b] = window.open(a)
    } else window.location.href = a
};
AmCharts.ifArray = function (a) {
    return a && 0 < a.length ? !0 : !1
};
AmCharts.callMethod = function (a, b) {
    var c;
    for (c = 0; c < b.length; c++) {
        var d = b[c];
        if (d) {
            if (d[a])d[a]();
            var e = d.length;
            if (0 < e) {
                var f;
                for (f = 0; f < e; f++) {
                    var g = d[f];
                    if (g && g[a])g[a]()
                }
            }
        }
    }
};
AmCharts.toNumber = function (a) {
    return "number" == typeof a ? a : Number(String(a).replace(/[^0-9\-.]+/g, ""))
};
AmCharts.toColor = function (a) {
    if ("" !== a && void 0 !== a)if (-1 != a.indexOf(",")) {
        a = a.split(",");
        var b;
        for (b = 0; b < a.length; b++) {
            var c = a[b].substring(a[b].length - 6, a[b].length);
            a[b] = "#" + c
        }
    } else a = a.substring(a.length - 6, a.length), a = "#" + a;
    return a
};
AmCharts.toCoordinate = function (a, b, c) {
    var d;
    void 0 !== a && (a = String(a), c && c < b && (b = c), d = Number(a), -1 != a.indexOf("!") && (d = b - Number(a.substr(1))), -1 != a.indexOf("%") && (d = b * Number(a.substr(0, a.length - 1)) / 100));
    return d
};
AmCharts.fitToBounds = function (a, b, c) {
    a < b && (a = b);
    a > c && (a = c);
    return a
};
AmCharts.isDefined = function (a) {
    return void 0 === a ? !1 : !0
};
AmCharts.stripNumbers = function (a) {
    return a.replace(/[0-9]+/g, "")
};
AmCharts.roundTo = function (a, b) {
    if (0 > b)return a;
    var c = Math.pow(10, b);
    return Math.round(a * c) / c
};
AmCharts.toFixed = function (a, b) {
    var c = String(Math.round(a * Math.pow(10, b)));
    if (0 < b) {
        var d = c.length;
        if (d < b) {
            var e;
            for (e = 0; e < b - d; e++)c = "0" + c
        }
        d = c.substring(0, c.length - b);
        "" === d && (d = 0);
        return d + "." + c.substring(c.length - b, c.length)
    }
    return String(c)
};
AmCharts.formatDuration = function (a, b, c, d, e, f) {
    var g = AmCharts.intervals, h = f.decimalSeparator;
    if (a >= g[b].contains) {
        var k = a - Math.floor(a / g[b].contains) * g[b].contains;
        "ss" == b && (k = AmCharts.formatNumber(k, f), 1 == k.split(h)[0].length && (k = "0" + k));
        ("mm" == b || "hh" == b) && 10 > k && (k = "0" + k);
        c = k + "" + d[b] + "" + c;
        a = Math.floor(a / g[b].contains);
        b = g[b].nextInterval;
        return AmCharts.formatDuration(a, b, c, d, e, f)
    }
    "ss" == b && (a = AmCharts.formatNumber(a, f), 1 == a.split(h)[0].length && (a = "0" + a));
    ("mm" == b || "hh" == b) && 10 > a && (a = "0" + a);
    c = a + "" +
    d[b] + "" + c;
    if (g[e].count > g[b].count)for (a = g[b].count; a < g[e].count; a++)b = g[b].nextInterval, "ss" == b || "mm" == b || "hh" == b ? c = "00" + d[b] + "" + c : "DD" == b && (c = "0" + d[b] + "" + c);
    ":" == c.charAt(c.length - 1) && (c = c.substring(0, c.length - 1));
    return c
};
AmCharts.formatNumber = function (a, b, c, d, e) {
    a = AmCharts.roundTo(a, b.precision);
    isNaN(c) && (c = b.precision);
    var f = b.decimalSeparator;
    b = b.thousandsSeparator;
    var g;
    g = 0 > a ? "-" : "";
    a = Math.abs(a);
    var h = String(a), k = !1;
    -1 != h.indexOf("e") && (k = !0);
    0 <= c && !k && (h = AmCharts.toFixed(a, c));
    var l = "";
    if (k)l = h; else {
        var h = h.split("."), k = String(h[0]), m;
        for (m = k.length; 0 <= m; m -= 3)l = m != k.length ? 0 !== m ? k.substring(m - 3, m) + b + l : k.substring(m - 3, m) + l : k.substring(m - 3, m);
        void 0 !== h[1] && (l = l + f + h[1]);
        void 0 !== c && 0 < c && "0" != l && (l = AmCharts.addZeroes(l,
            f, c))
    }
    l = g + l;
    "" === g && !0 === d && 0 !== a && (l = "+" + l);
    !0 === e && (l += "%");
    return l
};
AmCharts.addZeroes = function (a, b, c) {
    a = a.split(b);
    void 0 === a[1] && 0 < c && (a[1] = "0");
    return a[1].length < c ? (a[1] += "0", AmCharts.addZeroes(a[0] + b + a[1], b, c)) : void 0 !== a[1] ? a[0] + b + a[1] : a[0]
};
AmCharts.scientificToNormal = function (a) {
    var b;
    a = String(a).split("e");
    var c;
    if ("-" == a[1].substr(0, 1)) {
        b = "0.";
        for (c = 0; c < Math.abs(Number(a[1])) - 1; c++)b += "0";
        b += a[0].split(".").join("")
    } else {
        var d = 0;
        b = a[0].split(".");
        b[1] && (d = b[1].length);
        b = a[0].split(".").join("");
        for (c = 0; c < Math.abs(Number(a[1])) - d; c++)b += "0"
    }
    return b
};
AmCharts.toScientific = function (a, b) {
    if (0 === a)return "0";
    var c = Math.floor(Math.log(Math.abs(a)) * Math.LOG10E);
    Math.pow(10, c);
    mantissa = String(mantissa).split(".").join(b);
    return String(mantissa) + "e" + c
};
AmCharts.randomColor = function () {
    return "#" + ("00000" + (16777216 * Math.random() << 0).toString(16)).substr(-6)
};
AmCharts.hitTest = function (a, b, c) {
    var d = !1, e = a.x, f = a.x + a.width, g = a.y, h = a.y + a.height, k = AmCharts.isInRectangle;
    d || (d = k(e, g, b));
    d || (d = k(e, h, b));
    d || (d = k(f, g, b));
    d || (d = k(f, h, b));
    d || !0 === c || (d = AmCharts.hitTest(b, a, !0));
    return d
};
AmCharts.isInRectangle = function (a, b, c) {
    return a >= c.x - 5 && a <= c.x + c.width + 5 && b >= c.y - 5 && b <= c.y + c.height + 5 ? !0 : !1
};
AmCharts.isPercents = function (a) {
    if (-1 != String(a).indexOf("%"))return !0
};
AmCharts.findPosX = function (a) {
    var b = a, c = a.offsetLeft;
    if (a.offsetParent) {
        for (; a = a.offsetParent;)c += a.offsetLeft;
        for (; (b = b.parentNode) && b != document.body;)c -= b.scrollLeft || 0
    }
    return c
};
AmCharts.findPosY = function (a) {
    var b = a, c = a.offsetTop;
    if (a.offsetParent) {
        for (; a = a.offsetParent;)c += a.offsetTop;
        for (; (b = b.parentNode) && b != document.body;)c -= b.scrollTop || 0
    }
    return c
};
AmCharts.findIfFixed = function (a) {
    if (a.offsetParent)for (; a = a.offsetParent;)if ("fixed" == AmCharts.getStyle(a, "position"))return !0;
    return !1
};
AmCharts.findIfAuto = function (a) {
    return a.style && "auto" == AmCharts.getStyle(a, "overflow") ? !0 : a.parentNode ? AmCharts.findIfAuto(a.parentNode) : !1
};
AmCharts.findScrollLeft = function (a, b) {
    a.scrollLeft && (b += a.scrollLeft);
    return a.parentNode ? AmCharts.findScrollLeft(a.parentNode, b) : b
};
AmCharts.findScrollTop = function (a, b) {
    a.scrollTop && (b += a.scrollTop);
    return a.parentNode ? AmCharts.findScrollTop(a.parentNode, b) : b
};
AmCharts.formatValue = function (a, b, c, d, e, f, g, h) {
    if (b) {
        void 0 === e && (e = "");
        var k;
        for (k = 0; k < c.length; k++) {
            var l = c[k], m = b[l];
            void 0 !== m && (m = f ? AmCharts.addPrefix(m, h, g, d) : AmCharts.formatNumber(m, d), a = a.replace(new RegExp("\\[\\[" + e + "" + l + "\\]\\]", "g"), m))
        }
    }
    return a
};
AmCharts.formatDataContextValue = function (a, b) {
    if (a) {
        var c = a.match(/\[\[.*?\]\]/g), d;
        for (d = 0; d < c.length; d++) {
            var e = c[d], e = e.substr(2, e.length - 4);
            void 0 !== b[e] && (a = a.replace(new RegExp("\\[\\[" + e + "\\]\\]", "g"), b[e]))
        }
    }
    return a
};
AmCharts.massReplace = function (a, b) {
    for (var c in b)if (b.hasOwnProperty(c)) {
        var d = b[c];
        void 0 === d && (d = "");
        a = a.replace(c, d)
    }
    return a
};
AmCharts.cleanFromEmpty = function (a) {
    return a.replace(/\[\[[^\]]*\]\]/g, "")
};
AmCharts.addPrefix = function (a, b, c, d, e) {
    var f = AmCharts.formatNumber(a, d), g = "", h, k, l;
    if (0 === a)return "0";
    0 > a && (g = "-");
    a = Math.abs(a);
    if (1 < a)for (h = b.length - 1; -1 < h; h--) {
        if (a >= b[h].number && (k = a / b[h].number, l = Number(d.precision), 1 > l && (l = 1), c = AmCharts.roundTo(k, l), l = AmCharts.formatNumber(c, {
                precision: -1,
                decimalSeparator: d.decimalSeparator,
                thousandsSeparator: d.thousandsSeparator
            }), !e || k == c)) {
            f = g + "" + l + "" + b[h].prefix;
            break
        }
    } else for (h = 0; h < c.length; h++)if (a <= c[h].number) {
        k = a / c[h].number;
        l = Math.abs(Math.round(Math.log(k) *
        Math.LOG10E));
        k = AmCharts.roundTo(k, l);
        f = g + "" + k + "" + c[h].prefix;
        break
    }
    return f
};
AmCharts.remove = function (a) {
    a && a.remove()
};
AmCharts.recommended = function () {
    var a = "js";
    document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1") || swfobject && swfobject.hasFlashPlayerVersion("8") && (a = "flash");
    return a
};
AmCharts.getEffect = function (a) {
    ">" == a && (a = "easeOutSine");
    "<" == a && (a = "easeInSine");
    "elastic" == a && (a = "easeOutElastic");
    return a
};
AmCharts.getObjById = function (a, b) {
    var c, d;
    for (d = 0; d < a.length; d++) {
        var e = a[d];
        e.id == b && (c = e)
    }
    return c
};
AmCharts.applyTheme = function (a, b, c) {
    b || (b = AmCharts.theme);
    b && b[c] && AmCharts.extend(a, b[c])
};
AmCharts.isString = function (a) {
    return "string" == typeof a ? !0 : !1
};
AmCharts.extend = function (a, b, c) {
    for (var d in b)c ? a.hasOwnProperty(d) || (a[d] = b[d]) : a[d] = b[d];
    return a
};
AmCharts.copyProperties = function (a, b) {
    for (var c in a)a.hasOwnProperty(c) && "events" != c && void 0 !== a[c] && "function" != typeof a[c] && "cname" != c && (b[c] = a[c])
};
AmCharts.processObject = function (a, b, c) {
    !1 === a instanceof b && (a = AmCharts.extend(new b(c), a));
    return a
};
AmCharts.fixNewLines = function (a) {
    var b = RegExp("\\n", "g");
    a && (a = a.replace(b, "<br />"));
    return a
};
AmCharts.fixBrakes = function (a) {
    if (AmCharts.isModern) {
        var b = RegExp("<br>", "g");
        a && (a = a.replace(b, "\n"))
    } else a = AmCharts.fixNewLines(a);
    return a
};
AmCharts.deleteObject = function (a, b) {
    if (a) {
        if (void 0 === b || null === b)b = 20;
        if (0 !== b)if ("[object Array]" === Object.prototype.toString.call(a))for (var c = 0; c < a.length; c++)AmCharts.deleteObject(a[c], b - 1), a[c] = null; else if (a && !a.tagName)try {
            for (c in a)a[c] && ("object" == typeof a[c] && AmCharts.deleteObject(a[c], b - 1), "function" != typeof a[c] && (a[c] = null))
        } catch (d) {
        }
    }
};
AmCharts.bounce = function (a, b, c, d, e) {
    return (b /= e) < 1 / 2.75 ? 7.5625 * d * b * b + c : b < 2 / 2.75 ? d * (7.5625 * (b -= 1.5 / 2.75) * b + .75) + c : b < 2.5 / 2.75 ? d * (7.5625 * (b -= 2.25 / 2.75) * b + .9375) + c : d * (7.5625 * (b -= 2.625 / 2.75) * b + .984375) + c
};
AmCharts.easeInSine = function (a, b, c, d, e) {
    return -d * Math.cos(b / e * (Math.PI / 2)) + d + c
};
AmCharts.easeOutSine = function (a, b, c, d, e) {
    return d * Math.sin(b / e * (Math.PI / 2)) + c
};
AmCharts.easeOutElastic = function (a, b, c, d, e) {
    a = 1.70158;
    var f = 0, g = d;
    if (0 === b)return c;
    if (1 == (b /= e))return c + d;
    f || (f = .3 * e);
    g < Math.abs(d) ? (g = d, a = f / 4) : a = f / (2 * Math.PI) * Math.asin(d / g);
    return g * Math.pow(2, -10 * b) * Math.sin(2 * (b * e - a) * Math.PI / f) + d + c
};
AmCharts.AxisBase = AmCharts.Class({
    construct: function (a) {
        this.createEvents("clickItem", "rollOverItem", "rollOutItem");
        this.viY = this.viX = this.y = this.x = this.dy = this.dx = 0;
        this.axisThickness = 1;
        this.axisColor = "#000000";
        this.axisAlpha = 1;
        this.gridCount = this.tickLength = 5;
        this.gridAlpha = .15;
        this.gridThickness = 1;
        this.gridColor = "#000000";
        this.dashLength = 0;
        this.labelFrequency = 1;
        this.showLastLabel = this.showFirstLabel = !0;
        this.fillColor = "#FFFFFF";
        this.fillAlpha = 0;
        this.labelsEnabled = !0;
        this.labelRotation = 0;
        this.autoGridCount = !0;
        this.valueRollOverColor = "#CC0000";
        this.offset = 0;
        this.guides = [];
        this.visible = !0;
        this.counter = 0;
        this.guides = [];
        this.ignoreAxisWidth = this.inside = !1;
        this.minHorizontalGap = 75;
        this.minVerticalGap = 35;
        this.titleBold = !0;
        this.minorGridEnabled = !1;
        this.minorGridAlpha = .07;
        this.autoWrap = !1;
        this.titleAlign = "middle";
        this.labelOffset = 0;
        AmCharts.applyTheme(this, a, "AxisBase")
    }, zoom: function (a, b) {
        this.start = a;
        this.end = b;
        this.dataChanged = !0;
        this.draw()
    }, fixAxisPosition: function () {
        var a = this.position;
        "H" == this.orientation ?
            ("left" == a && (a = "bottom"), "right" == a && (a = "top")) : ("bottom" == a && (a = "left"), "top" == a && (a = "right"));
        this.position = a
    }, draw: function () {
        var a = this.chart;
        this.allLabels = [];
        this.counter = 0;
        this.destroy();
        this.fixAxisPosition();
        this.labels = [];
        var b = a.container, c = b.set();
        a.gridSet.push(c);
        this.set = c;
        b = b.set();
        a.axesLabelsSet.push(b);
        this.labelsSet = b;
        this.axisLine = new this.axisRenderer(this);
        this.autoGridCount ? ("V" == this.orientation ? (a = this.height / this.minVerticalGap, 3 > a && (a = 3)) : a = this.width / this.minHorizontalGap,
            this.gridCountR = Math.max(a, 1)) : this.gridCountR = this.gridCount;
        this.axisWidth = this.axisLine.axisWidth;
        this.addTitle()
    }, setOrientation: function (a) {
        this.orientation = a ? "H" : "V"
    }, addTitle: function () {
        var a = this.title;
        if (a) {
            var b = this.chart, c = this.titleColor;
            void 0 === c && (c = b.color);
            var d = this.titleFontSize;
            isNaN(d) && (d = b.fontSize + 1);
            this.titleLabel = AmCharts.text(b.container, a, c, b.fontFamily, d, this.titleAlign, this.titleBold)
        }
    }, positionTitle: function () {
        var a = this.titleLabel;
        if (a) {
            var b, c, d = this.labelsSet, e =
            {};
            0 < d.length() ? e = d.getBBox() : (e.x = 0, e.y = 0, e.width = this.viW, e.height = this.viH);
            d.push(a);
            var d = e.x, f = e.y;
            AmCharts.VML && (this.rotate ? d -= this.x : f -= this.y);
            var g = e.width, e = e.height, h = this.viW, k = this.viH, l = 0, m = a.getBBox().height / 2, n = this.inside, p = this.titleAlign;
            switch (this.position) {
                case "top":
                    b = "left" == p ? -1 : "right" == p ? h : h / 2;
                    c = f - 10 - m;
                    break;
                case "bottom":
                    b = "left" == p ? -1 : "right" == p ? h : h / 2;
                    c = f + e + 10 + m;
                    break;
                case "left":
                    b = d - 10 - m;
                    n && (b -= 5);
                    c = "left" == p ? k + 1 : "right" == p ? -1 : k / 2;
                    l = -90;
                    break;
                case "right":
                    b = d + g + 10 +
                    m - 3, n && (b += 7), c = "left" == p ? k + 2 : "right" == p ? -2 : k / 2, l = -90
            }
            this.marginsChanged ? (a.translate(b, c), this.tx = b, this.ty = c) : a.translate(this.tx, this.ty);
            this.marginsChanged = !1;
            0 !== l && a.rotate(l)
        }
    }, pushAxisItem: function (a, b) {
        var c = this, d = a.graphics();
        0 < d.length() && (b ? c.labelsSet.push(d) : c.set.push(d));
        if (d = a.getLabel())this.labelsSet.push(d), d.click(function (b) {
            c.handleMouse(b, a, "clickItem")
        }).mouseover(function (b) {
            c.handleMouse(b, a, "rollOverItem")
        }).mouseout(function (b) {
            c.handleMouse(b, a, "rollOutItem")
        })
    }, handleMouse: function (a,
                              b, c) {
        this.fire(c, {
            type: c,
            value: b.value,
            serialDataItem: b.serialDataItem,
            axis: this,
            target: b.label,
            chart: this.chart,
            event: a
        })
    }, addGuide: function (a) {
        for (var b = this.guides, c = !1, d = 0; d < b.length; d++)b[d] == a && (c = !0);
        c || b.push(a)
    }, removeGuide: function (a) {
        var b = this.guides, c;
        for (c = 0; c < b.length; c++)b[c] == a && b.splice(c, 1)
    }, handleGuideOver: function (a) {
        clearTimeout(this.chart.hoverInt);
        var b = a.graphics.getBBox(), c = b.x + b.width / 2, b = b.y + b.height / 2, d = a.fillColor;
        void 0 === d && (d = a.lineColor);
        this.chart.showBalloon(a.balloonText,
            d, !0, c, b)
    }, handleGuideOut: function (a) {
        this.chart.hideBalloon()
    }, addEventListeners: function (a, b) {
        var c = this;
        a.mouseover(function () {
            c.handleGuideOver(b)
        });
        a.mouseout(function () {
            c.handleGuideOut(b)
        })
    }, getBBox: function () {
        var a = this.labelsSet.getBBox();
        AmCharts.VML || (a = {x: a.x + this.x, y: a.y + this.y, width: a.width, height: a.height});
        return a
    }, destroy: function () {
        AmCharts.remove(this.set);
        AmCharts.remove(this.labelsSet);
        var a = this.axisLine;
        a && AmCharts.remove(a.set);
        AmCharts.remove(this.grid0)
    }
});
AmCharts.ValueAxis = AmCharts.Class({
    inherits: AmCharts.AxisBase, construct: function (a) {
        this.cname = "ValueAxis";
        this.createEvents("axisChanged", "logarithmicAxisFailed", "axisSelfZoomed", "axisZoomed");
        AmCharts.ValueAxis.base.construct.call(this, a);
        this.dataChanged = !0;
        this.stackType = "none";
        this.position = "left";
        this.unitPosition = "right";
        this.recalculateToPercents = this.includeHidden = this.includeGuidesInMinMax = this.integersOnly = !1;
        this.durationUnits = {DD: "d. ", hh: ":", mm: ":", ss: ""};
        this.scrollbar = !1;
        this.baseValue =
            0;
        this.radarCategoriesEnabled = !0;
        this.gridType = "polygons";
        this.useScientificNotation = !1;
        this.axisTitleOffset = 10;
        this.minMaxMultiplier = 1;
        this.logGridLimit = 2;
        AmCharts.applyTheme(this, a, this.cname)
    }, updateData: function () {
        0 >= this.gridCountR && (this.gridCountR = 1);
        this.totals = [];
        this.data = this.chart.chartData;
        var a = this.chart;
        "xy" != a.type && (this.stackGraphs("smoothedLine"), this.stackGraphs("line"), this.stackGraphs("column"), this.stackGraphs("step"));
        this.recalculateToPercents && this.recalculate();
        this.synchronizationMultiplier &&
        this.synchronizeWith ? (AmCharts.isString(this.synchronizeWith) && (this.synchronizeWith = a.getValueAxisById(this.synchronizeWith)), this.synchronizeWith && (this.synchronizeWithAxis(this.synchronizeWith), this.foundGraphs = !0)) : (this.foundGraphs = !1, this.getMinMax())
    }, draw: function () {
        AmCharts.ValueAxis.base.draw.call(this);
        var a = this.chart, b = this.set;
        "duration" == this.type && (this.duration = "ss");
        !0 === this.dataChanged && (this.updateData(), this.dataChanged = !1);
        if (this.logarithmic && (0 >= this.getMin(0, this.data.length -
            1) || 0 >= this.minimum))this.fire("logarithmicAxisFailed", {
            type: "logarithmicAxisFailed",
            chart: a
        }); else {
            this.grid0 = null;
            var c, d, e = a.dx, f = a.dy, g = !1, h = this.logarithmic;
            if (isNaN(this.min) || isNaN(this.max) || !this.foundGraphs || Infinity == this.min || -Infinity == this.max)g = !0; else {
                var k = this.labelFrequency, l = this.showFirstLabel, m = this.showLastLabel, n = 1, p = 0, q = Math.round((this.max - this.min) / this.step) + 1, r;
                !0 === h ? (r = Math.log(this.max) * Math.LOG10E - Math.log(this.minReal) * Math.LOG10E, this.stepWidth = this.axisWidth / r,
                r > this.logGridLimit && (q = Math.ceil(Math.log(this.max) * Math.LOG10E) + 1, p = Math.round(Math.log(this.minReal) * Math.LOG10E), q > this.gridCountR && (n = Math.ceil(q / this.gridCountR)))) : this.stepWidth = this.axisWidth / (this.max - this.min);
                var s = 0;
                1 > this.step && -1 < this.step && (s = AmCharts.getDecimals(this.step));
                this.integersOnly && (s = 0);
                s > this.maxDecCount && (s = this.maxDecCount);
                var w = this.precision;
                isNaN(w) || (s = w);
                this.max = AmCharts.roundTo(this.max, this.maxDecCount);
                this.min = AmCharts.roundTo(this.min, this.maxDecCount);
                d = {};
                d.precision = s;
                d.decimalSeparator = a.nf.decimalSeparator;
                d.thousandsSeparator = a.nf.thousandsSeparator;
                this.numberFormatter = d;
                var v, t = this.guides;
                c = t.length;
                if (0 < c) {
                    var u = this.fillAlpha;
                    for (d = this.fillAlpha = 0; d < c; d++) {
                        var x = t[d], E = NaN, A = x.above;
                        isNaN(x.toValue) || (E = this.getCoordinate(x.toValue), v = new this.axisItemRenderer(this, E, "", !0, NaN, NaN, x), this.pushAxisItem(v, A));
                        var z = NaN;
                        isNaN(x.value) || (z = this.getCoordinate(x.value), v = new this.axisItemRenderer(this, z, x.label, !0, NaN, (E - z) / 2, x), this.pushAxisItem(v,
                            A));
                        isNaN(E - z) || (v = new this.guideFillRenderer(this, z, E, x), this.pushAxisItem(v, A), v = v.graphics(), x.graphics = v, x.balloonText && this.addEventListeners(v, x))
                    }
                    this.fillAlpha = u
                }
                this.exponential = !1;
                for (d = p; d < q; d += n)t = AmCharts.roundTo(this.step * d + this.min, s), -1 != String(t).indexOf("e") && (this.exponential = !0, String(t).split("e"));
                this.duration && (this.maxInterval = AmCharts.getMaxInterval(this.max, this.duration));
                var s = this.step, H, t = this.minorGridAlpha;
                this.minorGridEnabled && (H = this.getMinorGridStep(s, this.stepWidth *
                s));
                for (d = p; d < q; d += n)if (p = s * d + this.min, h && this.max - this.min > 5 * this.min && (p -= this.min), p = AmCharts.roundTo(p, this.maxDecCount + 1), !this.integersOnly || Math.round(p) == p)if (isNaN(w) || Number(AmCharts.toFixed(p, w)) == p) {
                    !0 === h && (0 === p && (p = this.minReal), r > this.logGridLimit && (p = Math.pow(10, d)));
                    v = this.formatValue(p, !1, d);
                    Math.round(d / k) != d / k && (v = void 0);
                    if (0 === d && !l || d == q - 1 && !m)v = " ";
                    c = this.getCoordinate(p);
                    v = new this.axisItemRenderer(this, c, v, void 0, void 0, void 0, void 0, this.boldLabels);
                    this.pushAxisItem(v);
                    if (p == this.baseValue && "radar" != a.type) {
                        var F, G, x = this.viW, A = this.viH;
                        v = this.viX;
                        u = this.viY;
                        "H" == this.orientation ? 0 <= c && c <= x + 1 && (F = [c, c, c + e], G = [A, 0, f]) : 0 <= c && c <= A + 1 && (F = [0, x, x + e], G = [c, c, c + f]);
                        F && (c = AmCharts.fitToBounds(2 * this.gridAlpha, 0, 1), c = AmCharts.line(a.container, F, G, this.gridColor, c, 1, this.dashLength), c.translate(v, u), this.grid0 = c, a.axesSet.push(c), c.toBack())
                    }
                    if (!isNaN(H) && 0 < t && d < q - 1) {
                        v = this.gridAlpha;
                        this.gridAlpha = this.minorGridAlpha;
                        for (c = 1; c < s / H; c++)u = this.getCoordinate(p + H * c), u = new this.axisItemRenderer(this,
                            u, "", !1, 0, 0, !1, !1, 0, !0), this.pushAxisItem(u);
                        this.gridAlpha = v
                    }
                }
                e = this.baseValue;
                this.min > this.baseValue && this.max > this.baseValue && (e = this.min);
                this.min < this.baseValue && this.max < this.baseValue && (e = this.max);
                h && e < this.minReal && (e = this.minReal);
                this.baseCoord = this.getCoordinate(e);
                e = {type: "axisChanged", target: this, chart: a};
                e.min = h ? this.minReal : this.min;
                e.max = this.max;
                this.fire("axisChanged", e);
                this.axisCreated = !0
            }
            h = this.axisLine.set;
            e = this.labelsSet;
            this.positionTitle();
            "radar" != a.type ? (a = this.viX, f =
                this.viY, b.translate(a, f), e.translate(a, f)) : h.toFront();
            !this.visible || g ? (b.hide(), h.hide(), e.hide()) : (b.show(), h.show(), e.show());
            this.axisY = this.y - this.viY;
            this.axisX = this.x - this.viX
        }
    }, formatValue: function (a, b, c) {
        var d = this.exponential, e = this.logarithmic, f = this.numberFormatter, g = this.chart;
        !0 === this.logarithmic && (d = -1 != String(a).indexOf("e") ? !0 : !1);
        this.useScientificNotation && (d = !0);
        this.usePrefixes && (d = !1);
        d ? (valueText = -1 == String(a).indexOf("e") ? a.toExponential(15) : String(a), c = valueText.split("e"),
            b = Number(c[0]), c = Number(c[1]), b = AmCharts.roundTo(b, 14), 10 == b && (b = 1, c += 1), valueText = b + "e" + c, 0 === a && (valueText = "0"), 1 == a && (valueText = "1")) : (e && (d = String(a).split("."), d[1] ? (f.precision = d[1].length, 0 > c && (f.precision = Math.abs(c))) : f.precision = -1), valueText = this.usePrefixes ? AmCharts.addPrefix(a, g.prefixesOfBigNumbers, g.prefixesOfSmallNumbers, f, !b) : AmCharts.formatNumber(a, f, f.precision));
        this.duration && (valueText = AmCharts.formatDuration(a, this.duration, "", this.durationUnits, this.maxInterval, f));
        this.recalculateToPercents ?
            valueText += "%" : (f = this.unit) && (valueText = "left" == this.unitPosition ? f + valueText : valueText + f);
        this.labelFunction && (valueText = this.labelFunction(a, valueText, this).toString());
        return valueText
    }, getMinorGridStep: function (a, b) {
        var c = [5, 4, 2];
        60 > b && c.shift();
        for (var d = Math.floor(Math.log(Math.abs(a)) * Math.LOG10E), e = 0; e < c.length; e++) {
            var f = a / c[e], g = Math.floor(Math.log(Math.abs(f)) * Math.LOG10E);
            if (!(0 < Math.abs(d - g)))if (1 > a) {
                if (g = Math.pow(10, -g) * f, g == Math.round(g))return f
            } else if (f == Math.round(f))return f
        }
    },
    stackGraphs: function (a) {
        var b = this.stackType;
        "stacked" == b && (b = "regular");
        "line" == b && (b = "none");
        "100% stacked" == b && (b = "100%");
        this.stackType = b;
        var c = [], d = [], e = [], f = [], g, h = this.chart.graphs, k, l, m, n, p = this.baseValue, q = !1;
        if ("line" == a || "step" == a || "smoothedLine" == a)q = !0;
        if (q && ("regular" == b || "100%" == b))for (n = 0; n < h.length; n++)m = h[n], m.hidden || (l = m.type, m.chart == this.chart && m.valueAxis == this && a == l && m.stackable && (k && (m.stackGraph = k), k = m));
        for (k = this.start; k <= this.end; k++) {
            var r = 0;
            for (n = 0; n < h.length; n++)if (m =
                    h[n], m.hidden)m.newStack && (e[k] = NaN, d[k] = NaN); else if (l = m.type, m.chart == this.chart && m.valueAxis == this && a == l && m.stackable)if (l = this.data[k].axes[this.id].graphs[m.id], g = l.values.value, isNaN(g))m.newStack && (e[k] = NaN, d[k] = NaN); else {
                var s = AmCharts.getDecimals(g);
                r < s && (r = s);
                isNaN(f[k]) ? f[k] = Math.abs(g) : f[k] += Math.abs(g);
                f[k] = AmCharts.roundTo(f[k], r);
                s = m.fillToGraph;
                q && s && (s = this.data[k].axes[this.id].graphs[s.id]) && (l.values.open = s.values.value);
                "regular" == b && (q && (isNaN(c[k]) ? (c[k] = g, l.values.close = g,
                    l.values.open = this.baseValue) : (isNaN(g) ? l.values.close = c[k] : l.values.close = g + c[k], l.values.open = c[k], c[k] = l.values.close)), "column" == a && (m.newStack && (e[k] = NaN, d[k] = NaN), l.values.close = g, 0 > g ? (l.values.close = g, isNaN(d[k]) ? l.values.open = p : (l.values.close += d[k], l.values.open = d[k]), d[k] = l.values.close) : (l.values.close = g, isNaN(e[k]) ? l.values.open = p : (l.values.close += e[k], l.values.open = e[k]), e[k] = l.values.close)))
            }
        }
        for (k = this.start; k <= this.end; k++)for (n = 0; n < h.length; n++)(m = h[n], m.hidden) ? m.newStack && (e[k] =
            NaN, d[k] = NaN) : (l = m.type, m.chart == this.chart && m.valueAxis == this && a == l && m.stackable && (l = this.data[k].axes[this.id].graphs[m.id], g = l.values.value, isNaN(g) || (c = g / f[k] * 100, l.values.percents = c, l.values.total = f[k], m.newStack && (e[k] = NaN, d[k] = NaN), "100%" == b && (isNaN(d[k]) && (d[k] = 0), isNaN(e[k]) && (e[k] = 0), 0 > c ? (l.values.close = AmCharts.fitToBounds(c + d[k], -100, 100), l.values.open = d[k], d[k] = l.values.close) : (l.values.close = AmCharts.fitToBounds(c + e[k], -100, 100), l.values.open = e[k], e[k] = l.values.close)))))
    }, recalculate: function () {
        var a =
            this.chart, b = a.graphs, c;
        for (c = 0; c < b.length; c++) {
            var d = b[c];
            if (d.valueAxis == this) {
                var e = "value";
                if ("candlestick" == d.type || "ohlc" == d.type)e = "open";
                var f, g, h = this.end + 2, h = AmCharts.fitToBounds(this.end + 1, 0, this.data.length - 1), k = this.start;
                0 < k && k--;
                var l;
                g = this.start;
                d.compareFromStart && (g = 0);
                if (!isNaN(a.startTime) && (l = a.categoryAxis)) {
                    minDuration = l.minDuration();
                    var m = new Date(a.startTime + minDuration / 2), n = AmCharts.resetDateToMin(new Date(a.startTime), l.minPeriod).getTime();
                    AmCharts.resetDateToMin(new Date(m),
                        l.minPeriod).getTime() > n && g++
                }
                if (l = a.recalculateFromDate)a.dataDateFormat && (l = AmCharts.stringToDate(l, a.dataDateFormat)), g = a.getClosestIndex(a.chartData, "time", l.getTime(), !0, 0, a.chartData.length), h = a.chartData.length - 1;
                for (l = g; l <= h && (g = this.data[l].axes[this.id].graphs[d.id], f = g.values[e], isNaN(f)); l++);
                this.recBaseValue = f;
                for (e = k; e <= h; e++) {
                    g = this.data[e].axes[this.id].graphs[d.id];
                    g.percents = {};
                    var k = g.values, p;
                    for (p in k)g.percents[p] = "percents" != p ? k[p] / f * 100 - 100 : k[p]
                }
            }
        }
    }, getMinMax: function () {
        var a =
            !1, b = this.chart, c = b.graphs, d;
        for (d = 0; d < c.length; d++) {
            var e = c[d].type;
            ("line" == e || "step" == e || "smoothedLine" == e) && this.expandMinMax && (a = !0)
        }
        a && (0 < this.start && this.start--, this.end < this.data.length - 1 && this.end++);
        "serial" == b.type && (!0 !== b.categoryAxis.parseDates || a || this.end < this.data.length - 1 && this.end++);
        a = this.minMaxMultiplier;
        this.min = this.getMin(this.start, this.end);
        this.max = this.getMax();
        a = (this.max - this.min) * (a - 1);
        this.min -= a;
        this.max += a;
        a = this.guides.length;
        if (this.includeGuidesInMinMax && 0 < a)for (b =
                                                         0; b < a; b++)c = this.guides[b], c.toValue < this.min && (this.min = c.toValue), c.value < this.min && (this.min = c.value), c.toValue > this.max && (this.max = c.toValue), c.value > this.max && (this.max = c.value);
        isNaN(this.minimum) || (this.min = this.minimum);
        isNaN(this.maximum) || (this.max = this.maximum);
        this.min > this.max && (a = this.max, this.max = this.min, this.min = a);
        isNaN(this.minTemp) || (this.min = this.minTemp);
        isNaN(this.maxTemp) || (this.max = this.maxTemp);
        this.minReal = this.min;
        this.maxReal = this.max;
        0 === this.min && 0 === this.max && (this.max =
            9);
        this.min > this.max && (this.min = this.max - 1);
        a = this.min;
        b = this.max;
        c = this.max - this.min;
        d = 0 === c ? Math.pow(10, Math.floor(Math.log(Math.abs(this.max)) * Math.LOG10E)) / 10 : Math.pow(10, Math.floor(Math.log(Math.abs(c)) * Math.LOG10E)) / 10;
        isNaN(this.maximum) && isNaN(this.maxTemp) && (this.max = Math.ceil(this.max / d) * d + d);
        isNaN(this.minimum) && isNaN(this.minTemp) && (this.min = Math.floor(this.min / d) * d - d);
        0 > this.min && 0 <= a && (this.min = 0);
        0 < this.max && 0 >= b && (this.max = 0);
        "100%" == this.stackType && (this.min = 0 > this.min ? -100 : 0, this.max =
            0 > this.max ? 0 : 100);
        c = this.max - this.min;
        d = Math.pow(10, Math.floor(Math.log(Math.abs(c)) * Math.LOG10E)) / 10;
        this.step = Math.ceil(c / this.gridCountR / d) * d;
        c = Math.pow(10, Math.floor(Math.log(Math.abs(this.step)) * Math.LOG10E));
        c = this.fixStepE(c);
        d = Math.ceil(this.step / c);
        5 < d && (d = 10);
        5 >= d && 2 < d && (d = 5);
        this.step = Math.ceil(this.step / (c * d)) * c * d;
        1 > c ? (this.maxDecCount = Math.abs(Math.log(Math.abs(c)) * Math.LOG10E), this.maxDecCount = Math.round(this.maxDecCount), this.step = AmCharts.roundTo(this.step, this.maxDecCount + 1)) : this.maxDecCount =
            0;
        this.min = this.step * Math.floor(this.min / this.step);
        this.max = this.step * Math.ceil(this.max / this.step);
        0 > this.min && 0 <= a && (this.min = 0);
        0 < this.max && 0 >= b && (this.max = 0);
        1 < this.minReal && 1 < this.max - this.minReal && (this.minReal = Math.floor(this.minReal));
        c = Math.pow(10, Math.floor(Math.log(Math.abs(this.minReal)) * Math.LOG10E));
        0 === this.min && (this.minReal = c);
        0 === this.min && 1 < this.minReal && (this.minReal = 1);
        0 < this.min && 0 < this.minReal - this.step && (this.minReal = this.min + this.step < this.minReal ? this.min + this.step : this.min);
        c = Math.log(b) * Math.LOG10E - Math.log(a) * Math.LOG10E;
        this.logarithmic && (2 < c ? (this.minReal = this.min = Math.pow(10, Math.floor(Math.log(Math.abs(a)) * Math.LOG10E)), this.max = Math.pow(10, Math.ceil(Math.log(Math.abs(b)) * Math.LOG10E))) : (b = Math.pow(10, Math.floor(Math.log(Math.abs(this.min)) * Math.LOG10E)) / 10, a = Math.pow(10, Math.floor(Math.log(Math.abs(a)) * Math.LOG10E)) / 10, b < a && (this.minReal = this.min = 10 * a)))
    }, fixStepE: function (a) {
        a = a.toExponential(0).split("e");
        var b = Number(a[1]);
        9 == Number(a[0]) && b++;
        return this.generateNumber(1,
            b)
    }, generateNumber: function (a, b) {
        var c = "", d;
        d = 0 > b ? Math.abs(b) - 1 : Math.abs(b);
        var e;
        for (e = 0; e < d; e++)c += "0";
        return 0 > b ? Number("0." + c + String(a)) : Number(String(a) + c)
    }, getMin: function (a, b) {
        var c, d;
        for (d = a; d <= b; d++) {
            var e = this.data[d].axes[this.id].graphs, f;
            for (f in e)if (e.hasOwnProperty(f)) {
                var g = this.chart.getGraphById(f);
                if (g.includeInMinMax && (!g.hidden || this.includeHidden)) {
                    isNaN(c) && (c = Infinity);
                    this.foundGraphs = !0;
                    g = e[f].values;
                    this.recalculateToPercents && (g = e[f].percents);
                    var h;
                    if (this.minMaxField)h =
                        g[this.minMaxField], h < c && (c = h); else for (var k in g)g.hasOwnProperty(k) && "percents" != k && "total" != k && (h = g[k], h < c && (c = h))
                }
            }
        }
        return c
    }, getMax: function () {
        var a, b;
        for (b = this.start; b <= this.end; b++) {
            var c = this.data[b].axes[this.id].graphs, d;
            for (d in c)if (c.hasOwnProperty(d)) {
                var e = this.chart.getGraphById(d);
                if (e.includeInMinMax && (!e.hidden || this.includeHidden)) {
                    isNaN(a) && (a = -Infinity);
                    this.foundGraphs = !0;
                    e = c[d].values;
                    this.recalculateToPercents && (e = c[d].percents);
                    var f;
                    if (this.minMaxField)f = e[this.minMaxField],
                    f > a && (a = f); else for (var g in e)e.hasOwnProperty(g) && "percents" != g && "total" != g && (f = e[g], f > a && (a = f))
                }
            }
        }
        return a
    }, dispatchZoomEvent: function (a, b) {
        var c = {type: "axisZoomed", startValue: a, endValue: b, target: this, chart: this.chart};
        this.fire(c.type, c)
    }, zoomToValues: function (a, b) {
        if (b < a) {
            var c = b;
            b = a;
            a = c
        }
        a < this.min && (a = this.min);
        b > this.max && (b = this.max);
        c = {type: "axisSelfZoomed"};
        c.chart = this.chart;
        c.valueAxis = this;
        c.multiplier = this.axisWidth / Math.abs(this.getCoordinate(b) - this.getCoordinate(a));
        c.position = "V" ==
        this.orientation ? this.reversed ? this.getCoordinate(a) : this.getCoordinate(b) : this.reversed ? this.getCoordinate(b) : this.getCoordinate(a);
        this.fire(c.type, c)
    }, coordinateToValue: function (a) {
        if (isNaN(a))return NaN;
        var b = this.axisWidth, c = this.stepWidth, d = this.reversed, e = this.rotate, f = this.min, g = this.minReal;
        return !0 === this.logarithmic ? Math.pow(10, (e ? !0 === d ? (b - a) / c : a / c : !0 === d ? a / c : (b - a) / c) + Math.log(g) * Math.LOG10E) : !0 === d ? e ? f - (a - b) / c : a / c + f : e ? a / c + f : f - (a - b) / c
    }, getCoordinate: function (a) {
        if (isNaN(a))return NaN;
        var b = this.rotate, c = this.reversed, d = this.axisWidth, e = this.stepWidth, f = this.min, g = this.minReal;
        !0 === this.logarithmic ? (a = Math.log(a) * Math.LOG10E - Math.log(g) * Math.LOG10E, b = b ? !0 === c ? d - e * a : e * a : !0 === c ? e * a : d - e * a) : b = !0 === c ? b ? d - e * (a - f) : e * (a - f) : b ? e * (a - f) : d - e * (a - f);
        b = this.rotate ? b + (this.x - this.viX) : b + (this.y - this.viY);
        1E7 < Math.abs(b) && (b = 1E7 * (b / Math.abs(b)));
        return Math.round(b)
    }, synchronizeWithAxis: function (a) {
        this.synchronizeWith = a;
        this.listenTo(this.synchronizeWith, "axisChanged", this.handleSynchronization)
    },
    handleSynchronization: function (a) {
        var b = this.synchronizeWith;
        a = b.min;
        var c = b.max, b = b.step, d = this.synchronizationMultiplier;
        d && (this.min = a * d, this.max = c * d, this.step = b * d, a = Math.pow(10, Math.floor(Math.log(Math.abs(this.step)) * Math.LOG10E)), a = Math.abs(Math.log(Math.abs(a)) * Math.LOG10E), this.maxDecCount = a = Math.round(a), this.draw())
    }
});
AmCharts.RecAxis = AmCharts.Class({
    construct: function (a) {
        var b = a.chart, c = a.axisThickness, d = a.axisColor, e = a.axisAlpha, f = a.offset, g = a.dx, h = a.dy, k = a.viX, l = a.viY, m = a.viH, n = a.viW, p = b.container;
        "H" == a.orientation ? (d = AmCharts.line(p, [0, n], [0, 0], d, e, c), this.axisWidth = a.width, "bottom" == a.position ? (a = c / 2 + f + m + l - 1, c = k) : (a = -c / 2 - f + l + h, c = g + k)) : (this.axisWidth = a.height, "right" == a.position ? (d = AmCharts.line(p, [0, 0, -g], [0, m, m - h], d, e, c), a = l + h, c = c / 2 + f + g + n + k - 1) : (d = AmCharts.line(p, [0, 0], [0, m], d, e, c), a = l, c = -c / 2 - f + k));
        d.translate(c,
            a);
        b.axesSet.push(d);
        this.set = d
    }
});
AmCharts.RecItem = AmCharts.Class({
    construct: function (a, b, c, d, e, f, g, h, k, l, m) {
        b = Math.round(b);
        this.value = c;
        void 0 == c && (c = "");
        k || (k = 0);
        void 0 == d && (d = !0);
        var n = a.chart.fontFamily, p = a.fontSize;
        void 0 == p && (p = a.chart.fontSize);
        var q = a.color;
        void 0 == q && (q = a.chart.color);
        void 0 !== m && (q = m);
        var r = a.chart.container, s = r.set();
        this.set = s;
        var w = a.axisThickness, v = a.axisColor, t = a.axisAlpha, u = a.tickLength, x = a.gridAlpha, E = a.gridThickness, A = a.gridColor, z = a.dashLength, H = a.fillColor, F = a.fillAlpha, G = a.labelsEnabled;
        m = a.labelRotation;
        var V = a.counter, N = a.inside, ia = a.labelOffset, da = a.dx, ba = a.dy, Sa = a.orientation, na = a.position, ta = a.previousCoord, L = a.viH, Y = a.viW, $ = a.offset, oa, W;
        g ? (G = !0, isNaN(g.tickLength) || (u = g.tickLength), void 0 != g.lineColor && (A = g.lineColor), void 0 != g.color && (q = g.color), isNaN(g.lineAlpha) || (x = g.lineAlpha), isNaN(g.dashLength) || (z = g.dashLength), isNaN(g.lineThickness) || (E = g.lineThickness), !0 === g.inside && (N = !0), isNaN(g.labelRotation) || (m = g.labelRotation), isNaN(g.fontSize) || (p = g.fontSize), g.position && (na = g.position), void 0 !==
        g.boldLabel && (h = g.boldLabel), isNaN(g.labelOffset) || (ia = g.labelOffset)) : "" === c && (u = 0);
        W = "start";
        e && (W = "middle");
        var Z = m * Math.PI / 180, pa, I = 0, D = 0, aa = 0, S = pa = 0, Ta = 0;
        "V" == Sa && (m = 0);
        var T;
        G && (T = a.autoWrap && 0 === m ? AmCharts.wrappedText(r, c, q, n, p, W, h, e, 0) : AmCharts.text(r, c, q, n, p, W, h), W = T.getBBox(), S = W.width, Ta = W.height);
        if ("H" == Sa) {
            if (0 <= b && b <= Y + 1 && (0 < u && 0 < t && b + k <= Y + 1 && (oa = AmCharts.line(r, [b + k, b + k], [0, u], v, t, E), s.push(oa)), 0 < x && (W = AmCharts.line(r, [b, b + da, b + da], [L, L + ba, ba], A, x, E, z), s.push(W))), D = 0, I = b, g && 90 ==
                m && N && (I -= p), !1 === d ? (W = "start", D = "bottom" == na ? N ? D + u : D - u : N ? D - u : D + u, I += 3, e && (I += e / 2 - 3, W = "middle"), 0 < m && (W = "middle")) : W = "middle", 1 == V && 0 < F && !g && !l && ta < Y && (d = AmCharts.fitToBounds(b, 0, Y), ta = AmCharts.fitToBounds(ta, 0, Y), pa = d - ta, 0 < pa && (fill = AmCharts.rect(r, pa, a.height, H, F), fill.translate(d - pa + da, ba), s.push(fill))), "bottom" == na ? (D += L + p / 2 + $, N ? (0 < m ? (D = L - S / 2 * Math.sin(Z) - u - 3, I += S / 2 * Math.cos(Z) - 4 + 2) : 0 > m ? (D = L + S * Math.sin(Z) - u - 3 + 2, I += -S * Math.cos(Z) - Ta * Math.sin(Z) - 4) : D -= u + p + 3 + 3, D -= ia) : (0 < m ? (D = L + S / 2 * Math.sin(Z) + u +
                3, I -= S / 2 * Math.cos(Z)) : 0 > m ? (D = L + u + 3 - S / 2 * Math.sin(Z) + 2, I += S / 2 * Math.cos(Z)) : D += u + w + 3 + 3, D += ia)) : (D += ba + p / 2 - $, I += da, N ? (0 < m ? (D = S / 2 * Math.sin(Z) + u + 3, I -= S / 2 * Math.cos(Z)) : D += u + 3, D += ia) : (0 < m ? (D = -(S / 2) * Math.sin(Z) - u - 6, I += S / 2 * Math.cos(Z)) : D -= u + p + 3 + w + 3, D -= ia)), "bottom" == na ? pa = (N ? L - u - 1 : L + w - 1) + $ : (aa = da, pa = (N ? ba : ba - u - w + 1) - $), f && (I += f), f = I, 0 < m && (f += S / 2 * Math.cos(Z)), T && (p = 0, N && (p = S / 2 * Math.cos(Z)), f + p > Y + 2 || 0 > f))T.remove(), T = null
        } else {
            0 <= b && b <= L + 1 && (0 < u && 0 < t && b + k <= L + 1 && (oa = AmCharts.line(r, [0, u], [b + k, b + k], v, t, E), s.push(oa)),
            0 < x && (W = AmCharts.line(r, [0, da, Y + da], [b, b + ba, b + ba], A, x, E, z), s.push(W)));
            W = "end";
            if (!0 === N && "left" == na || !1 === N && "right" == na)W = "start";
            D = b - p / 2;
            1 == V && 0 < F && !g && !l && (d = AmCharts.fitToBounds(b, 0, L), ta = AmCharts.fitToBounds(ta, 0, L), Z = d - ta, fill = AmCharts.polygon(r, [0, a.width, a.width, 0], [0, 0, Z, Z], H, F), fill.translate(da, d - Z + ba), s.push(fill));
            D += p / 2;
            "right" == na ? (I += da + Y + $, D += ba, N ? (f || (D -= p / 2 + 3), I = I - (u + 4) - ia) : (I += u + 4 + w, D -= 2, I += ia)) : N ? (I += u + 4 - $, f || (D -= p / 2 + 3), g && (I += da, D += ba), I += ia) : (I += -u - w - 4 - 2 - $, D -= 2, I -= ia);
            oa &&
            ("right" == na ? (aa += da + $ + Y, pa += ba, aa = N ? aa - w : aa + w) : (aa -= $, N || (aa -= u + w)));
            f && (D += f);
            N = -3;
            "right" == na && (N += ba);
            T && (D > L + 1 || D < N) && (T.remove(), T = null)
        }
        oa && oa.translate(aa, pa);
        !1 === a.visible && (oa && oa.remove(), T && (T.remove(), T = null));
        T && (T.attr({"text-anchor": W}), T.translate(I, D), 0 !== m && T.rotate(-m, a.chart.backgroundColor), a.allLabels.push(T), " " != c && (this.label = T));
        l || (a.counter = 0 === V ? 1 : 0, a.previousCoord = b);
        0 === this.set.node.childNodes.length && this.set.remove()
    }, graphics: function () {
        return this.set
    }, getLabel: function () {
        return this.label
    }
});
AmCharts.RecFill = AmCharts.Class({
    construct: function (a, b, c, d) {
        var e = a.dx, f = a.dy, g = a.orientation, h = 0;
        if (c < b) {
            var k = b;
            b = c;
            c = k
        }
        var l = d.fillAlpha;
        isNaN(l) && (l = 0);
        k = a.chart.container;
        d = d.fillColor;
        "V" == g ? (b = AmCharts.fitToBounds(b, 0, a.viH), c = AmCharts.fitToBounds(c, 0, a.viH)) : (b = AmCharts.fitToBounds(b, 0, a.viW), c = AmCharts.fitToBounds(c, 0, a.viW));
        c -= b;
        isNaN(c) && (c = 4, h = 2, l = 0);
        0 > c && "object" == typeof d && (d = d.join(",").split(",").reverse());
        "V" == g ? (a = AmCharts.rect(k, a.width, c, d, l), a.translate(e, b - h + f)) : (a = AmCharts.rect(k,
            c, a.height, d, l), a.translate(b - h + e, f));
        this.set = k.set([a])
    }, graphics: function () {
        return this.set
    }, getLabel: function () {
    }
});
AmCharts.AmChart = AmCharts.Class({
    construct: function (a) {
        this.theme = a;
        this.version = "3.11.1";
        AmCharts.addChart(this);
        this.createEvents("dataUpdated", "init", "rendered", "drawn", "failed");
        this.height = this.width = "100%";
        this.dataChanged = !0;
        this.chartCreated = !1;
        this.previousWidth = this.previousHeight = 0;
        this.backgroundColor = "#FFFFFF";
        this.borderAlpha = this.backgroundAlpha = 0;
        this.color = this.borderColor = "#000000";
        this.fontFamily = "Verdana";
        this.fontSize = 11;
        this.usePrefixes = !1;
        this.precision = -1;
        this.percentPrecision =
            2;
        this.decimalSeparator = ".";
        this.thousandsSeparator = ",";
        this.labels = [];
        this.allLabels = [];
        this.titles = [];
        this.marginRight = this.marginLeft = this.autoMarginOffset = 0;
        this.timeOuts = [];
        this.creditsPosition = "top-left";
        var b = document.createElement("div"), c = b.style;
        c.overflow = "hidden";
        c.position = "relative";
        c.textAlign = "left";
        this.chartDiv = b;
        b = document.createElement("div");
        c = b.style;
        c.overflow = "hidden";
        c.position = "relative";
        c.textAlign = "left";
        this.legendDiv = b;
        this.titleHeight = 0;
        this.hideBalloonTime = 150;
        this.handDrawScatter =
            2;
        this.handDrawThickness = 1;
        this.prefixesOfBigNumbers = [{number: 1E3, prefix: "k"}, {number: 1E6, prefix: "M"}, {
            number: 1E9,
            prefix: "G"
        }, {number: 1E12, prefix: "T"}, {number: 1E15, prefix: "P"}, {number: 1E18, prefix: "E"}, {
            number: 1E21,
            prefix: "Z"
        }, {number: 1E24, prefix: "Y"}];
        this.prefixesOfSmallNumbers = [{number: 1E-24, prefix: "y"}, {number: 1E-21, prefix: "z"}, {
            number: 1E-18,
            prefix: "a"
        }, {number: 1E-15, prefix: "f"}, {number: 1E-12, prefix: "p"}, {number: 1E-9, prefix: "n"}, {
            number: 1E-6,
            prefix: "\u03bc"
        }, {number: .001, prefix: "m"}];
        this.panEventsEnabled = !0;
        AmCharts.bezierX = 3;
        AmCharts.bezierY = 6;
        this.product = "amcharts";
        this.animations = [];
        this.balloon = new AmCharts.AmBalloon(this.theme);
        this.balloon.chart = this;
        AmCharts.applyTheme(this, a, "AmChart")
    }, drawChart: function () {
        this.drawBackground();
        this.redrawLabels();
        this.drawTitles();
        this.brr()
    }, drawBackground: function () {
        AmCharts.remove(this.background);
        var a = this.container, b = this.backgroundColor, c = this.backgroundAlpha, d = this.set;
        AmCharts.isModern || 0 !== c || (c = .001);
        var e = this.updateWidth();
        this.realWidth = e;
        var f = this.updateHeight();
        this.realHeight = f;
        this.background = b = AmCharts.polygon(a, [0, e - 1, e - 1, 0], [0, 0, f - 1, f - 1], b, c, 1, this.borderColor, this.borderAlpha);
        d.push(b);
        if (b = this.backgroundImage)this.path && (b = this.path + b), this.bgImg = a = a.image(b, 0, 0, e, f), d.push(a)
    }, drawTitles: function () {
        var a = this.titles;
        if (AmCharts.ifArray(a)) {
            var b = 20, c;
            for (c = 0; c < a.length; c++) {
                var d = a[c], e = d.color;
                void 0 === e && (e = this.color);
                var f = d.size;
                isNaN(f) && (f = this.fontSize + 2);
                isNaN(d.alpha);
                var g = this.marginLeft, e = AmCharts.text(this.container,
                    d.text, e, this.fontFamily, f);
                e.translate(g + (this.realWidth - this.marginRight - g) / 2, b);
                e.node.style.pointerEvents = "none";
                g = !0;
                void 0 !== d.bold && (g = d.bold);
                g && e.attr({"font-weight": "bold"});
                e.attr({opacity: d.alpha});
                b += f + 6;
                this.freeLabelsSet.push(e)
            }
        }
    }, write: function (a) {
        if (a = "object" != typeof a ? document.getElementById(a) : a) {
            a.innerHTML = "";
            this.div = a;
            a.style.overflow = "hidden";
            a.style.textAlign = "left";
            var b = this.chartDiv, c = this.legendDiv, d = this.legend, e = c.style, f = b.style;
            this.measure();
            var g, h = document.createElement("div");
            g = h.style;
            g.position = "relative";
            this.containerDiv = h;
            a.appendChild(h);
            var k = this.exportConfig;
            k && AmCharts.AmExport && !this.AmExport && (this.AmExport = new AmCharts.AmExport(this, k));
            this.amExport && AmCharts.AmExport && (this.AmExport = AmCharts.extend(this.amExport, new AmCharts.AmExport(this), !0));
            this.AmExport && this.AmExport.init && this.AmExport.init();
            if (d)switch (d = this.addLegend(d, d.divId), d.position) {
                case "bottom":
                    h.appendChild(b);
                    h.appendChild(c);
                    break;
                case "top":
                    h.appendChild(c);
                    h.appendChild(b);
                    break;
                case "absolute":
                    g.width = a.style.width;
                    g.height = a.style.height;
                    e.position = "absolute";
                    f.position = "absolute";
                    void 0 !== d.left && (e.left = d.left + "px");
                    void 0 !== d.right && (e.right = d.right + "px");
                    void 0 !== d.top && (e.top = d.top + "px");
                    void 0 !== d.bottom && (e.bottom = d.bottom + "px");
                    d.marginLeft = 0;
                    d.marginRight = 0;
                    h.appendChild(b);
                    h.appendChild(c);
                    break;
                case "right":
                    g.width = a.style.width;
                    g.height = a.style.height;
                    e.position = "relative";
                    f.position = "absolute";
                    h.appendChild(b);
                    h.appendChild(c);
                    break;
                case "left":
                    g.width = a.style.width;
                    g.height = a.style.height;
                    e.position = "absolute";
                    f.position = "relative";
                    h.appendChild(b);
                    h.appendChild(c);
                    break;
                case "outside":
                    h.appendChild(b)
            } else h.appendChild(b);
            this.listenersAdded || (this.addListeners(), this.listenersAdded = !0);
            this.initChart()
        }
    }, createLabelsSet: function () {
        AmCharts.remove(this.labelsSet);
        this.labelsSet = this.container.set();
        this.freeLabelsSet.push(this.labelsSet)
    }, initChart: function () {
        AmCharts.callInitHandler(this);
        AmCharts.applyLang(this.language, this);
        var a = this.numberFormatter;
        a && (isNaN(a.precision) || (this.precision = a.precision), void 0 !== a.thousandsSeparator && (this.thousandsSeparator = a.thousandsSeparator), void 0 !== a.decimalSeparator && (this.decimalSeparator = a.decimalSeparator));
        (a = this.percentFormatter) && !isNaN(a.precision) && (this.percentPrecision = a.precision);
        this.nf = {
            precision: this.precision,
            thousandsSeparator: this.thousandsSeparator,
            decimalSeparator: this.decimalSeparator
        };
        this.pf = {
            precision: this.percentPrecision,
            thousandsSeparator: this.thousandsSeparator,
            decimalSeparator: this.decimalSeparator
        };
        this.divIsFixed = AmCharts.findIfFixed(this.chartDiv);
        this.previousHeight = this.divRealHeight;
        this.previousWidth = this.divRealWidth;
        this.destroy();
        this.startInterval();
        a = 0;
        document.attachEvent && !window.opera && (a = 1);
        this.dmouseX = this.dmouseY = 0;
        var b = document.getElementsByTagName("html")[0];
        b && window.getComputedStyle && (b = window.getComputedStyle(b, null)) && (this.dmouseY = AmCharts.removePx(b.getPropertyValue("margin-top")), this.dmouseX = AmCharts.removePx(b.getPropertyValue("margin-left")));
        this.mouseMode = a;
        (a = this.container) ? (a.container.innerHTML = "", this.chartDiv.appendChild(a.container), a.setSize(this.realWidth, this.realHeight)) : a = new AmCharts.AmDraw(this.chartDiv, this.realWidth, this.realHeight, this);
        AmCharts.VML || AmCharts.SVG ? (a.handDrawn = this.handDrawn, a.handDrawScatter = this.handDrawScatter, a.handDrawThickness = this.handDrawThickness, this.container = a, this.set && this.set.remove(), this.set = a.set(), this.gridSet && this.gridSet.remove(), this.gridSet = a.set(), this.cursorLineSet && this.cursorLineSet.remove(),
            this.cursorLineSet = a.set(), this.graphsBehindSet && this.graphsBehindSet.remove(), this.graphsBehindSet = a.set(), this.bulletBehindSet && this.bulletBehindSet.remove(), this.bulletBehindSet = a.set(), this.columnSet && this.columnSet.remove(), this.columnSet = a.set(), this.graphsSet && this.graphsSet.remove(), this.graphsSet = a.set(), this.trendLinesSet && this.trendLinesSet.remove(), this.trendLinesSet = a.set(), this.axesLabelsSet && this.axesLabelsSet.remove(), this.axesLabelsSet = a.set(), this.axesSet && this.axesSet.remove(),
            this.axesSet = a.set(), this.cursorSet && this.cursorSet.remove(), this.cursorSet = a.set(), this.scrollbarsSet && this.scrollbarsSet.remove(), this.scrollbarsSet = a.set(), this.bulletSet && this.bulletSet.remove(), this.bulletSet = a.set(), this.freeLabelsSet && this.freeLabelsSet.remove(), this.freeLabelsSet = a.set(), this.balloonsSet && this.balloonsSet.remove(), this.balloonsSet = a.set(), this.zoomButtonSet && this.zoomButtonSet.remove(), this.zoomButtonSet = a.set(), this.linkSet && this.linkSet.remove(), this.linkSet = a.set(), this.renderFix()) :
            this.fire("failed", {type: "failed", chart: this})
    }, measure: function () {
        var a = this.div;
        if (a) {
            var b = this.chartDiv, c = a.offsetWidth, d = a.offsetHeight, e = this.container;
            a.clientHeight && (c = a.clientWidth, d = a.clientHeight);
            var f = AmCharts.removePx(AmCharts.getStyle(a, "padding-left")), g = AmCharts.removePx(AmCharts.getStyle(a, "padding-right")), h = AmCharts.removePx(AmCharts.getStyle(a, "padding-top")), k = AmCharts.removePx(AmCharts.getStyle(a, "padding-bottom"));
            isNaN(f) || (c -= f);
            isNaN(g) || (c -= g);
            isNaN(h) || (d -= h);
            isNaN(k) ||
            (d -= k);
            f = a.style;
            a = f.width;
            f = f.height;
            -1 != a.indexOf("px") && (c = AmCharts.removePx(a));
            -1 != f.indexOf("px") && (d = AmCharts.removePx(f));
            a = AmCharts.toCoordinate(this.width, c);
            f = AmCharts.toCoordinate(this.height, d);
            this.balloon = AmCharts.processObject(this.balloon, AmCharts.AmBalloon, this.theme);
            this.balloon.chart = this;
            (a != this.previousWidth || f != this.previousHeight) && 0 < a && 0 < f && (b.style.width = a + "px", b.style.height = f + "px", e && e.setSize(a, f));
            this.balloon.setBounds(2, 2, a - 2, f);
            this.realWidth = a;
            this.realHeight = f;
            this.divRealWidth = c;
            this.divRealHeight = d
        }
    }, destroy: function () {
        this.chartDiv.innerHTML = "";
        this.clearTimeOuts();
        this.interval && clearInterval(this.interval);
        this.interval = NaN
    }, clearTimeOuts: function () {
        var a = this.timeOuts;
        if (a) {
            var b;
            for (b = 0; b < a.length; b++)clearTimeout(a[b])
        }
        this.timeOuts = []
    }, clear: function (a) {
        AmCharts.callMethod("clear", [this.chartScrollbar, this.scrollbarV, this.scrollbarH, this.chartCursor]);
        this.chartCursor = this.scrollbarH = this.scrollbarV = this.chartScrollbar = null;
        this.clearTimeOuts();
        this.interval && clearInterval(this.interval);
        this.container && (this.container.remove(this.chartDiv), this.container.remove(this.legendDiv));
        a || AmCharts.removeChart(this)
    }, setMouseCursor: function (a) {
        "auto" == a && AmCharts.isNN && (a = "default");
        this.chartDiv.style.cursor = a;
        this.legendDiv.style.cursor = a
    }, redrawLabels: function () {
        this.labels = [];
        var a = this.allLabels;
        this.createLabelsSet();
        var b;
        for (b = 0; b < a.length; b++)this.drawLabel(a[b])
    }, drawLabel: function (a) {
        if (this.container) {
            var b = a.y, c = a.text, d = a.align, e =
                a.size, f = a.color, g = a.rotation, h = a.alpha, k = a.bold, l = AmCharts.toCoordinate(a.x, this.realWidth), b = AmCharts.toCoordinate(b, this.realHeight);
            l || (l = 0);
            b || (b = 0);
            void 0 === f && (f = this.color);
            isNaN(e) && (e = this.fontSize);
            d || (d = "start");
            "left" == d && (d = "start");
            "right" == d && (d = "end");
            "center" == d && (d = "middle", g ? b = this.realHeight - b + b / 2 : l = this.realWidth / 2 - l);
            void 0 === h && (h = 1);
            void 0 === g && (g = 0);
            b += e / 2;
            c = AmCharts.text(this.container, c, f, this.fontFamily, e, d, k, h);
            c.translate(l, b);
            0 !== g && c.rotate(g);
            a.url ? (c.setAttr("cursor",
                "pointer"), c.click(function () {
                AmCharts.getURL(a.url)
            })) : c.node.style.pointerEvents = "none";
            this.labelsSet.push(c);
            this.labels.push(c)
        }
    }, addLabel: function (a, b, c, d, e, f, g, h, k, l) {
        a = {x: a, y: b, text: c, align: d, size: e, color: f, alpha: h, rotation: g, bold: k, url: l};
        this.container && this.drawLabel(a);
        this.allLabels.push(a)
    }, clearLabels: function () {
        var a = this.labels, b;
        for (b = a.length - 1; 0 <= b; b--)a[b].remove();
        this.labels = [];
        this.allLabels = []
    }, updateHeight: function () {
        var a = this.divRealHeight, b = this.legend;
        if (b) {
            var c = this.legendDiv.offsetHeight,
                b = b.position;
            if ("top" == b || "bottom" == b) {
                a -= c;
                if (0 > a || isNaN(a))a = 0;
                this.chartDiv.style.height = a + "px"
            }
        }
        return a
    }, updateWidth: function () {
        var a = this.divRealWidth, b = this.divRealHeight, c = this.legend;
        if (c) {
            var d = this.legendDiv, e = d.offsetWidth;
            isNaN(c.width) || (e = c.width);
            var f = d.offsetHeight, d = d.style, g = this.chartDiv.style, c = c.position;
            if ("right" == c || "left" == c) {
                a -= e;
                if (0 > a || isNaN(a))a = 0;
                g.width = a + "px";
                "left" == c ? g.left = e + "px" : d.left = a + "px";
                b > f && (d.top = (b - f) / 2 + "px")
            }
        }
        return a
    }, getTitleHeight: function () {
        var a =
            0, b = this.titles;
        if (0 < b.length) {
            var a = 15, c;
            for (c = 0; c < b.length; c++) {
                var d = b[c].size;
                isNaN(d) && (d = this.fontSize + 2);
                a += d + 6
            }
        }
        return a
    }, addTitle: function (a, b, c, d, e) {
        isNaN(b) && (b = this.fontSize + 2);
        a = {text: a, size: b, color: c, alpha: d, bold: e};
        this.titles.push(a);
        return a
    }, addMouseWheel: function () {
        var a = this;
        window.addEventListener && !a.wheelAdded && (window.addEventListener("DOMMouseScroll", function (b) {
            a.handleWheel.call(a, b)
        }, !1), document.addEventListener("mousewheel", function (b) {
            a.handleWheel.call(a, b)
        }, !1), a.wheelAdded = !0)
    }, handleWheel: function (a) {
        if (this.mouseIsOver) {
            var b = 0;
            a || (a = window.event);
            a.wheelDelta ? b = a.wheelDelta / 120 : a.detail && (b = -a.detail / 3);
            b && this.handleWheelReal(b, a.shiftKey);
            a.preventDefault && a.preventDefault()
        }
    }, handleWheelReal: function (a) {
    }, addListeners: function () {
        var a = this, b = a.chartDiv;
        document.addEventListener ? (a.panEventsEnabled && (b.style.msTouchAction = "none", "ontouchstart"in document.documentElement && (b.addEventListener("touchstart", function (b) {
            a.handleTouchMove.call(a, b);
            a.handleTouchStart.call(a,
                b)
        }, !0), b.addEventListener("touchmove", function (b) {
            a.handleTouchMove.call(a, b)
        }, !0), b.addEventListener("touchend", function (b) {
            a.handleTouchEnd.call(a, b)
        }, !0))), b.addEventListener("mousedown", function (b) {
            a.mouseIsOver = !0;
            a.handleMouseMove.call(a, b);
            a.handleMouseDown.call(a, b)
        }, !0), b.addEventListener("mouseover", function (b) {
            a.handleMouseOver.call(a, b)
        }, !0), b.addEventListener("mouseout", function (b) {
            a.handleMouseOut.call(a, b)
        }, !0)) : (b.attachEvent("onmousedown", function (b) {
            a.handleMouseDown.call(a, b)
        }),
            b.attachEvent("onmouseover", function (b) {
                a.handleMouseOver.call(a, b)
            }), b.attachEvent("onmouseout", function (b) {
            a.handleMouseOut.call(a, b)
        }))
    }, dispDUpd: function () {
        var a;
        this.dispatchDataUpdated && (this.dispatchDataUpdated = !1, a = "dataUpdated", this.fire(a, {
            type: a,
            chart: this
        }));
        this.chartCreated || (a = "init", this.fire(a, {type: a, chart: this}));
        this.chartRendered || (a = "rendered", this.fire(a, {type: a, chart: this}), this.chartRendered = !0);
        a = "drawn";
        this.fire(a, {type: a, chart: this})
    }, validateSize: function () {
        var a = this;
        a.measure();
        var b = a.legend;
        if ((a.realWidth != a.previousWidth || a.realHeight != a.previousHeight) && 0 < a.realWidth && 0 < a.realHeight) {
            a.sizeChanged = !0;
            if (b) {
                clearTimeout(a.legendInitTO);
                var c = setTimeout(function () {
                    b.invalidateSize()
                }, 100);
                a.timeOuts.push(c);
                a.legendInitTO = c
            }
            a.marginsUpdated = "xy" != a.type ? !1 : !0;
            clearTimeout(a.initTO);
            c = setTimeout(function () {
                a.initChart()
            }, 150);
            a.timeOuts.push(c);
            a.initTO = c
        }
        a.renderFix();
        b && b.renderFix()
    }, invalidateSize: function () {
        this.previousHeight = this.previousWidth = NaN;
        this.invalidateSizeReal()
    },
    invalidateSizeReal: function () {
        var a = this;
        a.marginsUpdated = !1;
        clearTimeout(a.validateTO);
        var b = setTimeout(function () {
            a.validateSize()
        }, 5);
        a.timeOuts.push(b);
        a.validateTO = b
    }, validateData: function (a) {
        this.chartCreated && (this.dataChanged = !0, this.marginsUpdated = "xy" != this.type ? !1 : !0, this.initChart(a))
    }, validateNow: function () {
        this.chartRendered = !1;
        this.write(this.div)
    }, showItem: function (a) {
        a.hidden = !1;
        this.initChart()
    }, hideItem: function (a) {
        a.hidden = !0;
        this.initChart()
    }, hideBalloon: function () {
        var a = this;
        clearInterval(a.hoverInt);
        clearTimeout(a.balloonTO);
        a.hoverInt = setTimeout(function () {
            a.hideBalloonReal.call(a)
        }, a.hideBalloonTime)
    }, cleanChart: function () {
    }, hideBalloonReal: function () {
        var a = this.balloon;
        a && a.hide()
    }, showBalloon: function (a, b, c, d, e) {
        var f = this;
        clearTimeout(f.balloonTO);
        clearInterval(f.hoverInt);
        f.balloonTO = setTimeout(function () {
            f.showBalloonReal.call(f, a, b, c, d, e)
        }, 1)
    }, showBalloonReal: function (a, b, c, d, e) {
        this.handleMouseMove();
        var f = this.balloon;
        f.enabled && (f.followCursor(!1), f.changeColor(b),
            !c || f.fixedPosition ? (f.setPosition(d, e), f.followCursor(!1)) : f.followCursor(!0), a && f.showBalloon(a))
    }, handleTouchMove: function (a) {
        this.hideBalloon();
        var b = this.chartDiv;
        a.touches && (a = a.touches.item(0), this.mouseX = a.pageX - AmCharts.findPosX(b), this.mouseY = a.pageY - AmCharts.findPosY(b))
    }, handleMouseOver: function (a) {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !0
    }, handleMouseOut: function (a) {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !1
    }, handleMouseMove: function (a) {
        if (this.mouseIsOver) {
            var b = this.chartDiv;
            a || (a = window.event);
            var c, d;
            if (a) {
                this.posX = AmCharts.findPosX(b);
                this.posY = AmCharts.findPosY(b);
                switch (this.mouseMode) {
                    case 1:
                        c = a.clientX - this.posX;
                        d = a.clientY - this.posY;
                        if (!this.divIsFixed) {
                            var b = document.body, e, f;
                            b && (e = b.scrollLeft, y1 = b.scrollTop);
                            if (b = document.documentElement)f = b.scrollLeft, y2 = b.scrollTop;
                            e = Math.max(e, f);
                            f = Math.max(y1, y2);
                            c += e;
                            d += f
                        }
                        break;
                    case 0:
                        this.divIsFixed ? (c = a.clientX - this.posX, d = a.clientY - this.posY) : (c = a.pageX - this.posX, d = a.pageY - this.posY)
                }
                a.touches && (a = a.touches.item(0),
                    c = a.pageX - this.posX, d = a.pageY - this.posY);
                this.mouseX = c - this.dmouseX;
                this.mouseY = d - this.dmouseY
            }
        }
    }, handleTouchStart: function (a) {
        this.handleMouseDown(a)
    }, handleTouchEnd: function (a) {
        AmCharts.resetMouseOver();
        this.handleReleaseOutside(a)
    }, handleReleaseOutside: function (a) {
    }, handleMouseDown: function (a) {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !0;
        a && a.preventDefault && a.preventDefault()
    }, addLegend: function (a, b) {
        a = AmCharts.processObject(a, AmCharts.AmLegend, this.theme);
        a.divId = b;
        var c;
        c = "object" != typeof b &&
        b ? document.getElementById(b) : b;
        this.legend = a;
        a.chart = this;
        c ? (a.div = c, a.position = "outside", a.autoMargins = !1) : a.div = this.legendDiv;
        c = this.handleLegendEvent;
        this.listenTo(a, "showItem", c);
        this.listenTo(a, "hideItem", c);
        this.listenTo(a, "clickMarker", c);
        this.listenTo(a, "rollOverItem", c);
        this.listenTo(a, "rollOutItem", c);
        this.listenTo(a, "rollOverMarker", c);
        this.listenTo(a, "rollOutMarker", c);
        this.listenTo(a, "clickLabel", c);
        return a
    }, removeLegend: function () {
        this.legend = void 0;
        this.legendDiv.innerHTML = ""
    }, handleResize: function () {
        (AmCharts.isPercents(this.width) ||
        AmCharts.isPercents(this.height)) && this.invalidateSizeReal();
        this.renderFix()
    }, renderFix: function () {
        if (!AmCharts.VML) {
            var a = this.container;
            a && a.renderFix()
        }
    }, getSVG: function () {
        if (AmCharts.hasSVG)return this.container
    }, animate: function (a, b, c, d, e, f, g) {
        a["an_" + b] && AmCharts.removeFromArray(this.animations, a["an_" + b]);
        c = {obj: a, frame: 0, attribute: b, from: c, to: d, time: e, effect: f, suffix: g};
        a["an_" + b] = c;
        this.animations.push(c);
        return c
    }, setLegendData: function (a) {
        var b = this.legend;
        b && b.setData(a)
    }, startInterval: function () {
        var a =
            this;
        clearInterval(a.interval);
        a.interval = setInterval(function () {
            a.updateAnimations.call(a)
        }, AmCharts.updateRate)
    }, stopAnim: function (a) {
        AmCharts.removeFromArray(this.animations, a)
    }, updateAnimations: function () {
        var a;
        this.container && this.container.update();
        for (a = this.animations.length - 1; 0 <= a; a--) {
            var b = this.animations[a], c = 1E3 * b.time / AmCharts.updateRate, d = b.frame + 1, e = b.obj, f = b.attribute;
            if (d <= c) {
                b.frame++;
                var g = Number(b.from), h = Number(b.to) - g, c = AmCharts[b.effect](0, d, g, h, c);
                0 === h ? (this.animations.splice(a,
                    1), e.node.style[f] = Number(b.to) + b.suffix) : e.node.style[f] = c + b.suffix
            } else e.node.style[f] = Number(b.to) + b.suffix, this.animations.splice(a, 1)
        }
    }, inIframe: function () {
        try {
            return window.self !== window.top
        } catch (a) {
            return !0
        }
    }, brr: function () {
        var a = window.location.hostname.split("."), b;
        2 <= a.length && (b = a[a.length - 2] + "." + a[a.length - 1]);
        this.amLink && (a = this.amLink.parentNode) && a.removeChild(this.amLink);
        a = this.creditsPosition;
        if ("amcharts.com" != b || !0 === this.inIframe()) {
            var c = b = 0, d = this.realWidth, e = this.realHeight;
            if ("serial" == this.type || "xy" == this.type)b = this.marginLeftReal, c = this.marginTopReal, d = b + this.plotAreaWidth, e = c + this.plotAreaHeight;
            var f = "http://www.amcharts.com/javascript-charts/", g = "JavaScript charts", h = "JS chart by amCharts";
            "ammap" == this.product && (f = "http://www.ammap.com/javascript-maps/", g = "Interactive JavaScript maps", h = "JS map by amCharts");
            var k = document.createElement("a"), h = document.createTextNode(h);
            k.setAttribute("href", f);
            k.setAttribute("title", g);
            k.appendChild(h);
            this.chartDiv.appendChild(k);
            this.amLink = k;
            f = k.style;
            f.position = "absolute";
            f.textDecoration = "none";
            f.color = this.color;
            f.fontFamily = this.fontFamily;
            f.fontSize = this.fontSize + "px";
            f.opacity = .7;
            f.display = "block";
            var g = k.offsetWidth, k = k.offsetHeight, h = 5 + b, l = c + 5;
            "bottom-left" == a && (h = 5 + b, l = e - k - 3);
            "bottom-right" == a && (h = d - g - 5, l = e - k - 3);
            "top-right" == a && (h = d - g - 5, l = c + 5);
            f.left = h + "px";
            f.top = l + "px"
        }
    }
});
AmCharts.Slice = AmCharts.Class({
    construct: function () {
    }
});
AmCharts.SerialDataItem = AmCharts.Class({
    construct: function () {
    }
});
AmCharts.GraphDataItem = AmCharts.Class({
    construct: function () {
    }
});
AmCharts.Guide = AmCharts.Class({
    construct: function (a) {
        this.cname = "Guide";
        AmCharts.applyTheme(this, a, this.cname)
    }
});
AmCharts.AmGraph = AmCharts.Class({
    construct: function (a) {
        this.cname = "AmGraph";
        this.createEvents("rollOverGraphItem", "rollOutGraphItem", "clickGraphItem", "doubleClickGraphItem", "rightClickGraphItem", "clickGraph", "rollOverGraph", "rollOutGraph");
        this.type = "line";
        this.stackable = !0;
        this.columnCount = 1;
        this.columnIndex = 0;
        this.centerCustomBullets = this.showBalloon = !0;
        this.maxBulletSize = 50;
        this.minBulletSize = 4;
        this.balloonText = "[[value]]";
        this.hidden = this.scrollbar = this.animationPlayed = !1;
        this.pointPosition = "middle";
        this.depthCount = 1;
        this.includeInMinMax = !0;
        this.negativeBase = 0;
        this.visibleInLegend = !0;
        this.showAllValueLabels = !1;
        this.showBulletsAt = this.showBalloonAt = "close";
        this.lineThickness = 1;
        this.dashLength = 0;
        this.connect = !0;
        this.lineAlpha = 1;
        this.bullet = "none";
        this.bulletBorderThickness = 2;
        this.bulletBorderAlpha = 0;
        this.bulletAlpha = 1;
        this.bulletSize = 8;
        this.hideBulletsCount = this.bulletOffset = 0;
        this.labelPosition = "top";
        this.cornerRadiusTop = 0;
        this.cursorBulletAlpha = 1;
        this.gradientOrientation = "vertical";
        this.dy = this.dx =
            0;
        this.periodValue = "";
        this.clustered = !0;
        this.periodSpan = 1;
        this.y = this.x = 0;
        this.switchable = !0;
        this.tcc = this.minDistance = 1;
        AmCharts.applyTheme(this, a, this.cname)
    }, draw: function () {
        var a = this.chart;
        isNaN(this.precision) || (this.numberFormatter ? this.numberFormatter.precision = this.precision : this.numberFormatter = {
            precision: this.precision,
            decimalSeparator: a.decimalSeparator,
            thousandsSeparator: a.thousandsSeparator
        });
        var b = a.container;
        this.container = b;
        this.destroy();
        var c = b.set(), d = b.set();
        this.behindColumns ?
            (a.graphsBehindSet.push(c), a.bulletBehindSet.push(d)) : (a.graphsSet.push(c), a.bulletSet.push(d));
        var e = this.bulletAxis;
        AmCharts.isString(e) && (this.bulletAxis = a.getValueAxisById(e));
        this.bulletSet = d;
        this.scrollbar || (e = a.marginLeftReal, a = a.marginTopReal, c.translate(e, a), d.translate(e, a));
        b = b.set();
        AmCharts.remove(this.columnsSet);
        c.push(b);
        this.set = c;
        this.columnsSet = b;
        this.columnsArray = [];
        this.ownColumns = [];
        this.allBullets = [];
        this.animationArray = [];
        AmCharts.ifArray(this.data) && (c = !1, "xy" == this.chart.type ?
        this.xAxis.axisCreated && this.yAxis.axisCreated && (c = !0) : this.valueAxis.axisCreated && (c = !0), !this.hidden && c && this.createGraph())
    }, createGraph: function () {
        var a = this, b = a.chart;
        "inside" == a.labelPosition && "column" != a.type && (a.labelPosition = "bottom");
        a.startAlpha = b.startAlpha;
        a.seqAn = b.sequencedAnimation;
        a.baseCoord = a.valueAxis.baseCoord;
        void 0 === a.fillAlphas && (a.fillAlphas = 0);
        a.bulletColorR = a.bulletColor;
        void 0 === a.bulletColorR && (a.bulletColorR = a.lineColorR, a.bulletColorNegative = a.negativeLineColor);
        void 0 ===
        a.bulletAlpha && (a.bulletAlpha = a.lineAlpha);
        clearTimeout(a.playedTO);
        if (!isNaN(a.valueAxis.min) && !isNaN(a.valueAxis.max)) {
            switch (b.type) {
                case "serial":
                    a.categoryAxis && (a.createSerialGraph(), "candlestick" == a.type && 1 > a.valueAxis.minMaxMultiplier && a.positiveClip(a.set));
                    break;
                case "radar":
                    a.createRadarGraph();
                    break;
                case "xy":
                    a.createXYGraph(), a.positiveClip(a.set)
            }
            a.playedTO = setTimeout(function () {
                a.setAnimationPlayed.call(a)
            }, 500 * a.chart.startDuration)
        }
    }, setAnimationPlayed: function () {
        this.animationPlayed = !0
    }, createXYGraph: function () {
        var a = [], b = [], c = this.xAxis, d = this.yAxis;
        this.pmh = d.viH + 1;
        this.pmw = c.viW + 1;
        this.pmy = this.pmx = 0;
        var e;
        for (e = this.start; e <= this.end; e++) {
            var f = this.data[e].axes[c.id].graphs[this.id], g = f.values, h = g.x, k = g.y, g = c.getCoordinate(h), l = d.getCoordinate(k);
            !isNaN(h) && !isNaN(k) && (a.push(g), b.push(l), (h = this.createBullet(f, g, l, e)) || (h = 0), k = this.labelText) && (f = this.createLabel(f, g, l, k), this.allBullets.push(f), this.positionLabel(g, l, f, this.labelPosition, h))
        }
        this.drawLineGraph(a, b);
        this.launchAnimation()
    },
    createRadarGraph: function () {
        var a = this.valueAxis.stackType, b = [], c = [], d, e, f;
        for (f = this.start; f <= this.end; f++) {
            var g = this.data[f].axes[this.valueAxis.id].graphs[this.id], h;
            h = "none" == a || "3d" == a ? g.values.value : g.values.close;
            if (isNaN(h))this.drawLineGraph(b, c), b = [], c = []; else {
                var k = this.y - (this.valueAxis.getCoordinate(h) - this.height), l = 180 - 360 / (this.end - this.start + 1) * f;
                h = k * Math.sin(l / 180 * Math.PI);
                k *= Math.cos(l / 180 * Math.PI);
                b.push(h);
                c.push(k);
                (l = this.createBullet(g, h, k, f)) || (l = 0);
                var m = this.labelText;
                m && (g = this.createLabel(g, h, k, m), this.allBullets.push(g), this.positionLabel(h, k, g, this.labelPosition, l));
                isNaN(d) && (d = h);
                isNaN(e) && (e = k)
            }
        }
        b.push(d);
        c.push(e);
        this.drawLineGraph(b, c);
        this.launchAnimation()
    }, positionLabel: function (a, b, c, d, e) {
        var f = c.getBBox();
        switch (d) {
            case "left":
                a -= (f.width + e) / 2 + 2;
                break;
            case "top":
                b -= (e + f.height) / 2 + 1;
                break;
            case "right":
                a += (f.width + e) / 2 + 2;
                break;
            case "bottom":
                b += (e + f.height) / 2 + 1
        }
        c.translate(a, b)
    }, getGradRotation: function () {
        var a = 270;
        "horizontal" == this.gradientOrientation &&
        (a = 0);
        return this.gradientRotation = a
    }, createSerialGraph: function () {
        this.dashLengthSwitched = this.fillColorsSwitched = this.lineColorSwitched = void 0;
        var a = this.chart, b = this.id, c = this.index, d = this.data, e = this.chart.container, f = this.valueAxis, g = this.type, h = this.columnWidthReal, k = this.showBulletsAt;
        isNaN(this.columnWidth) || (h = this.columnWidth);
        isNaN(h) && (h = .8);
        var l = this.useNegativeColorIfDown, m = this.width, n = this.height, p = this.y, q = this.rotate, r = this.columnCount, s = AmCharts.toCoordinate(this.cornerRadiusTop,
            h / 2), w = this.connect, v = [], t = [], u, x, E, A, z = this.chart.graphs.length, H, F = this.dx / this.tcc, G = this.dy / this.tcc, V = f.stackType, N = this.labelPosition, ia = this.start, da = this.end, ba = this.scrollbar, Sa = this.categoryAxis, na = this.baseCoord, ta = this.negativeBase, L = this.columnIndex, Y = this.lineThickness, $ = this.lineAlpha, oa = this.lineColorR, W = this.dashLength, Z = this.set, pa, I = N, D = this.getGradRotation(), aa = this.chart.columnSpacing, S = Sa.cellWidth, Ta = (S * h - r) / r;
        aa > Ta && (aa = Ta);
        var T, y, bb, kb = n + 1, lb = m + 1, cb = 0, mb = 0, nb, ob, db, eb, pb =
            this.fillColorsR, Da = this.negativeFillColors, wa = this.negativeLineColor, Ua = this.fillAlphas, Va = this.negativeFillAlphas;
        "object" == typeof Ua && (Ua = Ua[0]);
        "object" == typeof Va && (Va = Va[0]);
        var fb = f.getCoordinate(f.min);
        f.logarithmic && (fb = f.getCoordinate(f.minReal));
        this.minCoord = fb;
        this.resetBullet && (this.bullet = "none");
        if (!(ba || "line" != g && "smoothedLine" != g && "step" != g || (1 == d.length && "step" != g && "none" == this.bullet && (this.bullet = "round", this.resetBullet = !0), !Da && void 0 == wa || l))) {
            var Na = ta;
            Na > f.max && (Na = f.max);
            Na < f.min && (Na = f.min);
            f.logarithmic && (Na = f.minReal);
            var Aa = f.getCoordinate(Na), Hb = f.getCoordinate(f.max);
            q ? (kb = n, lb = Math.abs(Hb - Aa), nb = n, ob = Math.abs(fb - Aa), eb = mb = 0, f.reversed ? (cb = 0, db = Aa) : (cb = Aa, db = 0)) : (lb = m, kb = Math.abs(Hb - Aa), ob = m, nb = Math.abs(fb - Aa), db = cb = 0, f.reversed ? (eb = p, mb = Aa) : eb = Aa + 1)
        }
        var Ba = Math.round;
        this.pmx = Ba(cb);
        this.pmy = Ba(mb);
        this.pmh = Ba(kb);
        this.pmw = Ba(lb);
        this.nmx = Ba(db);
        this.nmy = Ba(eb);
        this.nmh = Ba(nb);
        this.nmw = Ba(ob);
        AmCharts.isModern || (this.nmy = this.nmx = 0, this.nmh = this.height);
        this.clustered ||
        (r = 1);
        h = "column" == g ? (S * h - aa * (r - 1)) / r : S * h;
        1 > h && (h = 1);
        var J;
        if ("line" == g || "step" == g || "smoothedLine" == g) {
            if (0 < ia) {
                for (J = ia - 1; -1 < J; J--)if (T = d[J], y = T.axes[f.id].graphs[b], bb = y.values.value, !isNaN(bb)) {
                    ia = J;
                    break
                }
                if (this.lineColorField)for (J = ia; -1 < J; J--)if (T = d[J], y = T.axes[f.id].graphs[b], y.lineColor) {
                    this.bulletColorSwitched = this.lineColorSwitched = y.lineColor;
                    break
                }
                if (this.fillColorsField)for (J = ia; -1 < J; J--)if (T = d[J], y = T.axes[f.id].graphs[b], y.fillColors) {
                    this.fillColorsSwitched = y.fillColors;
                    break
                }
                if (this.dashLengthField)for (J =
                                                  ia; -1 < J; J--)if (T = d[J], y = T.axes[f.id].graphs[b], !isNaN(y.dashLength)) {
                    this.dashLengthSwitched = y.dashLength;
                    break
                }
            }
            if (da < d.length - 1)for (J = da + 1; J < d.length; J++)if (T = d[J], y = T.axes[f.id].graphs[b], bb = y.values.value, !isNaN(bb)) {
                da = J;
                break
            }
        }
        da < d.length - 1 && da++;
        var O = [], P = [], Ea = !1;
        if ("line" == g || "step" == g || "smoothedLine" == g)if (this.stackable && "regular" == V || "100%" == V || this.fillToGraph)Ea = !0;
        var Ib = this.noStepRisers, gb = -1E3, hb = -1E3, ib = this.minDistance, Fa = !0, Wa = !1;
        for (J = ia; J <= da; J++) {
            T = d[J];
            y = T.axes[f.id].graphs[b];
            y.index = J;
            var Xa, Ga = NaN;
            if (l && void 0 == this.openField)for (var qb = J + 1; qb < d.length && (!d[qb] || !(Xa = d[J + 1].axes[f.id].graphs[b]) || !Xa.values || (Ga = Xa.values.value, isNaN(Ga))); qb++);
            var X, Q, R, ea, la = NaN, C = NaN, B = NaN, M = NaN, K = NaN, Ha = NaN, xa = NaN, Ia = NaN, ya = NaN, ca = NaN, ja = NaN, fa = NaN, ga = NaN, U = NaN, rb = NaN, sb = NaN, ka = NaN, ra = void 0, Ca = pb, Oa = Ua, ua = oa, qa, sa, tb = this.proCandlesticks, Ya = this.pattern;
            void 0 != y.pattern && (Ya = y.pattern);
            void 0 != y.color && (Ca = y.color);
            y.fillColors && (Ca = y.fillColors);
            isNaN(y.alpha) || (Oa = y.alpha);
            isNaN(y.dashLength) || (W = y.dashLength);
            var va = y.values;
            f.recalculateToPercents && (va = y.percents);
            if (va) {
                U = this.stackable && "none" != V && "3d" != V ? va.close : va.value;
                if ("candlestick" == g || "ohlc" == g)U = va.close, sb = va.low, xa = f.getCoordinate(sb), rb = va.high, ya = f.getCoordinate(rb);
                ka = va.open;
                B = f.getCoordinate(U);
                isNaN(ka) || (K = f.getCoordinate(ka), l && (Ga = ka, ka = K = NaN));
                l && (void 0 == this.openField ? Xa && (Xa.isNegative = Ga < U ? !0 : !1, isNaN(Ga) && (y.isNegative = !Fa)) : y.isNegative = Ga > U ? !0 : !1);
                if (!ba)switch (this.showBalloonAt) {
                    case "close":
                        y.y =
                            B;
                        break;
                    case "open":
                        y.y = K;
                        break;
                    case "high":
                        y.y = ya;
                        break;
                    case "low":
                        y.y = xa
                }
                var la = T.x[Sa.id], Pa = this.periodSpan - 1, ma = Math.floor(S / 2) + Math.floor(Pa * S / 2), za = ma, Jb = 0;
                "left" == this.stepDirection && (Jb = (2 * S + Pa * S) / 2, la -= Jb);
                "start" == this.pointPosition && (la -= S / 2 + Math.floor(Pa * S / 2), ma = 0, za = Math.floor(S) + Math.floor(Pa * S));
                "end" == this.pointPosition && (la += S / 2 + Math.floor(Pa * S / 2), ma = Math.floor(S) + Math.floor(Pa * S), za = 0);
                if (Ib) {
                    var ub = this.columnWidth;
                    isNaN(ub) || (ma *= ub, za *= ub)
                }
                ba || (y.x = la);
                -1E5 > la && (la = -1E5);
                la >
                m + 1E5 && (la = m + 1E5);
                q ? (C = B, M = K, K = B = la, isNaN(ka) && !this.fillToGraph && (M = na), Ha = xa, Ia = ya) : (M = C = la, isNaN(ka) && !this.fillToGraph && (K = na));
                if (!tb && U < ka || tb && U < pa)y.isNegative = !0, Da && (Ca = Da), Va && (Oa = Va), void 0 != wa && (ua = wa);
                Wa = !1;
                isNaN(U) || (l ? U > Ga ? (Fa && (Wa = !0), Fa = !1) : (Fa || (Wa = !0), Fa = !0) : y.isNegative = U < ta ? !0 : !1, pa = U);
                switch (g) {
                    case "line":
                        if (isNaN(U))w || (this.drawLineGraph(v, t, O, P), v = [], t = [], O = [], P = []); else {
                            if (Math.abs(C - gb) >= ib || Math.abs(B - hb) >= ib)v.push(C), t.push(B), gb = C, hb = B;
                            ca = C;
                            ja = B;
                            fa = C;
                            ga = B;
                            !Ea || isNaN(K) ||
                            isNaN(M) || (O.push(M), P.push(K));
                            if (Wa || void 0 != y.lineColor || void 0 != y.fillColors || !isNaN(y.dashLength))this.drawLineGraph(v, t, O, P), v = [C], t = [B], O = [], P = [], !Ea || isNaN(K) || isNaN(M) || (O.push(M), P.push(K)), l ? Fa ? (this.lineColorSwitched = oa, this.fillColorsSwitched = pb) : (this.lineColorSwitched = wa, this.fillColorsSwitched = Da) : (this.lineColorSwitched = y.lineColor, this.fillColorsSwitched = y.fillColors), this.dashLengthSwitched = y.dashLength;
                            y.gap && (this.drawLineGraph(v, t, O, P), v = [], t = [], O = [], P = [])
                        }
                        break;
                    case "smoothedLine":
                        if (isNaN(U))w ||
                        (this.drawSmoothedGraph(v, t, O, P), v = [], t = [], O = [], P = []); else {
                            if (Math.abs(C - gb) >= ib || Math.abs(B - hb) >= ib)v.push(C), t.push(B), gb = C, hb = B;
                            ca = C;
                            ja = B;
                            fa = C;
                            ga = B;
                            !Ea || isNaN(K) || isNaN(M) || (O.push(M), P.push(K));
                            void 0 == y.lineColor && void 0 == y.fillColors && isNaN(y.dashLength) || (this.drawSmoothedGraph(v, t, O, P), v = [C], t = [B], O = [], P = [], !Ea || isNaN(K) || isNaN(M) || (O.push(M), P.push(K)), this.lineColorSwitched = y.lineColor, this.fillColorsSwitched = y.fillColors, this.dashLengthSwitched = y.dashLength);
                            y.gap && (this.drawSmoothedGraph(v,
                                t, O, P), v = [], t = [], O = [], P = [])
                        }
                        break;
                    case "step":
                        if (!isNaN(U)) {
                            if (void 0 == y.lineColor && void 0 == y.fillColors && isNaN(y.dashLength) || (this.drawLineGraph(v, t, O, P), v = [], t = [], O = [], P = [], this.lineColorSwitched = y.lineColor, this.fillColorsSwitched = y.fillColors, this.dashLengthSwitched = y.dashLength), q ? (isNaN(u) || (v.push(u), t.push(B - ma)), t.push(B - ma), v.push(C), t.push(B + za), v.push(C), !Ea || isNaN(K) || isNaN(M) || (O.push(E), P.push(K - ma), O.push(M), P.push(K - ma), O.push(M), P.push(K + za))) : (isNaN(x) || (t.push(x), v.push(u), t.push(x),
                                    v.push(C - ma)), v.push(C - ma), t.push(B), v.push(C + za), t.push(B), !Ea || isNaN(K) || isNaN(M) || (O.push(M - ma), P.push(A), O.push(M - ma), P.push(K), O.push(M + za), P.push(K))), u = C, x = B, E = M, A = K, ca = C, ja = B, fa = C, ga = B, Wa && (this.drawLineGraph(v, t, O, P), v = [], t = [], O = [], P = [], l && (Fa ? (this.lineColorSwitched = oa, this.fillColorsSwitched = pb) : (this.lineColorSwitched = wa, this.fillColorsSwitched = Da))), Ib || y.gap)u = x = NaN, this.drawLineGraph(v, t, O, P), v = [], t = [], O = [], P = []
                        } else if (!w) {
                            if (1 >= this.periodSpan || 1 < this.periodSpan && C - u > ma + za)u = x = NaN;
                            this.drawLineGraph(v, t, O, P);
                            v = [];
                            t = [];
                            O = [];
                            P = []
                        }
                        break;
                    case "column":
                        qa = ua;
                        void 0 != y.lineColor && (qa = y.lineColor);
                        var Kb = this.topRadius;
                        if (!isNaN(U)) {
                            l || (y.isNegative = U < ta ? !0 : !1);
                            y.isNegative && (Da && (Ca = Da), void 0 != wa && (qa = wa));
                            var Lb = f.min, Mb = f.max;
                            if (!(U < Lb && ka < Lb || U > Mb && ka > Mb))if (q) {
                                "3d" == V ? (Q = B - (r / 2 - this.depthCount + 1) * (h + aa) + aa / 2 + G * L, X = M + F * L) : (Q = Math.floor(B - (r / 2 - L) * (h + aa) + aa / 2), X = M);
                                R = h;
                                ca = C;
                                ja = Q + h / 2;
                                fa = C;
                                ga = Q + h / 2;
                                Q + R > n + L * G && (R = n - Q + L * G);
                                Q < L * G && (R += Q, Q = L * G);
                                ea = C - M;
                                var Yb = X;
                                X = AmCharts.fitToBounds(X,
                                    0, m);
                                ea += Yb - X;
                                ea = AmCharts.fitToBounds(ea, -X, m - X + F * L);
                                Q < n && 0 < R && (ra = new AmCharts.Cuboid(e, ea, R, F - a.d3x, G - a.d3y, Ca, Oa, Y, qa, $, D, s, q, W, Ya, Kb), "bottom" != N && "inside" != N && "middle" != N && (N = f.reversed ? "left" : "right", 0 > U && (N = f.reversed ? "right" : "left")), "regular" == V || "100%" == V) && (ca += this.dx)
                            } else {
                                "3d" == V ? (X = C - (r / 2 - this.depthCount + 1) * (h + aa) + aa / 2 + F * L, Q = K + G * L) : (X = C - (r / 2 - L) * (h + aa) + aa / 2, Q = K);
                                R = h;
                                ca = X + h / 2;
                                ja = B;
                                fa = X + h / 2;
                                ga = B;
                                X + R > m + L * F && (R = m - X + L * F);
                                X < L * F && (R += X - L * F, X = L * F);
                                ea = B - K;
                                var Zb = Q;
                                Q = AmCharts.fitToBounds(Q, this.dy,
                                    n);
                                ea += Zb - Q;
                                ea = AmCharts.fitToBounds(ea, -Q + G * L, n - Q);
                                if (X < m + L * F && 0 < R)if (this.showOnAxis && (Q -= G / 2), ra = new AmCharts.Cuboid(e, R, ea, F - a.d3x, G - a.d3y, Ca, Oa, Y, qa, this.lineAlpha, D, s, q, W, Ya, Kb), 0 > U && "middle" != N && "inside" != N)N = "bottom"; else if (N = I, "regular" == V || "100%" == V)ja += this.dy
                            }
                            if (ra && (sa = ra.set, y.columnGraphics = sa, sa.translate(X, Q), this.columnsSet.push(sa), (y.url || this.showHandOnHover) && sa.setAttr("cursor", "pointer"), !ba)) {
                                "none" == V && (H = q ? (this.end + 1 - J) * z - c : z * J + c);
                                "3d" == V && (q ? (H = (this.end + 1 - J) * z - c - 1E3 *
                                this.depthCount, ca += F * this.columnIndex, fa += F * this.columnIndex, y.y += F * this.columnIndex) : (H = (z - c) * (J + 1) + 1E3 * this.depthCount, ca += 3, ja += G * this.columnIndex + 7, ga += G * this.columnIndex, y.y += G * this.columnIndex));
                                if ("regular" == V || "100%" == V)"inside" != N && (N = "middle"), H = q ? 0 < va.value ? (this.end + 1 - J) * z + c : (this.end + 1 - J) * z - c : 0 < va.value ? z * J + c : z * J - c;
                                this.columnsArray.push({column: ra, depth: H});
                                y.x = q ? Q + R / 2 : X + R / 2;
                                this.ownColumns.push(ra);
                                this.animateColumns(ra, J, C, M, B, K);
                                this.addListeners(sa, y)
                            }
                        }
                        break;
                    case "candlestick":
                        if (!isNaN(ka) && !isNaN(U)) {
                            var jb, vb;
                            qa = ua;
                            void 0 != y.lineColor && (qa = y.lineColor);
                            if (q) {
                                if (Q = B - h / 2, X = M, R = h, Q + R > n && (R = n - Q), 0 > Q && (R += Q, Q = 0), Q < n && 0 < R) {
                                    var wb, xb;
                                    U > ka ? (wb = [C, Ia], xb = [M, Ha]) : (wb = [M, Ia], xb = [C, Ha]);
                                    !isNaN(Ia) && !isNaN(Ha) && B < n && 0 < B && (jb = AmCharts.line(e, wb, [B, B], qa, $, Y), vb = AmCharts.line(e, xb, [B, B], qa, $, Y));
                                    ea = C - M;
                                    ra = new AmCharts.Cuboid(e, ea, R, F, G, Ca, Ua, Y, qa, $, D, s, q, W, Ya)
                                }
                            } else if (X = C - h / 2, Q = K + Y / 2, R = h, X + R > m && (R = m - X), 0 > X && (R += X, X = 0), ea = B - K, X < m && 0 < R) {
                                tb && U >= ka && (Oa = 0);
                                var ra = new AmCharts.Cuboid(e, R, ea, F, G, Ca, Oa,
                                    Y, qa, $, D, s, q, W, Ya), yb, zb;
                                U > ka ? (yb = [B, ya], zb = [K, xa]) : (yb = [K, ya], zb = [B, xa]);
                                !isNaN(ya) && !isNaN(xa) && C < m && 0 < C && (jb = AmCharts.line(e, [C, C], yb, qa, $, Y), vb = AmCharts.line(e, [C, C], zb, qa, $, Y))
                            }
                            ra && (sa = ra.set, y.columnGraphics = sa, Z.push(sa), sa.translate(X, Q - Y / 2), (y.url || this.showHandOnHover) && sa.setAttr("cursor", "pointer"), jb && (Z.push(jb), Z.push(vb)), ca = C, ja = B, q ? (ga = B, fa = C, "open" == k && (fa = M), "high" == k && (fa = Ia), "low" == k && (fa = Ha)) : (ga = B, "open" == k && (ga = K), "high" == k && (ga = ya), "low" == k && (ga = xa), fa = C), ba || (y.x = q ? Q + R /
                            2 : X + R / 2, this.animateColumns(ra, J, C, M, B, K), this.addListeners(sa, y)))
                        }
                        break;
                    case "ohlc":
                        if (!(isNaN(ka) || isNaN(rb) || isNaN(sb) || isNaN(U))) {
                            U < ka && (y.isNegative = !0, void 0 != wa && (ua = wa));
                            var Ab, Bb, Cb;
                            if (q) {
                                var Db = B - h / 2, Db = AmCharts.fitToBounds(Db, 0, n), Nb = AmCharts.fitToBounds(B, 0, n), Eb = B + h / 2, Eb = AmCharts.fitToBounds(Eb, 0, n);
                                Bb = AmCharts.line(e, [M, M], [Db, Nb], ua, $, Y, W);
                                0 < B && B < n && (Ab = AmCharts.line(e, [Ha, Ia], [B, B], ua, $, Y, W));
                                Cb = AmCharts.line(e, [C, C], [Nb, Eb], ua, $, Y, W);
                                ga = B;
                                fa = C;
                                "open" == k && (fa = M);
                                "high" == k && (fa = Ia);
                                "low" == k && (fa = Ha)
                            } else {
                                var Fb = C - h / 2, Fb = AmCharts.fitToBounds(Fb, 0, m), Ob = AmCharts.fitToBounds(C, 0, m), Gb = C + h / 2, Gb = AmCharts.fitToBounds(Gb, 0, m);
                                Bb = AmCharts.line(e, [Fb, Ob], [K, K], ua, $, Y, W);
                                0 < C && C < m && (Ab = AmCharts.line(e, [C, C], [xa, ya], ua, $, Y, W));
                                Cb = AmCharts.line(e, [Ob, Gb], [B, B], ua, $, Y, W);
                                ga = B;
                                "open" == k && (ga = K);
                                "high" == k && (ga = ya);
                                "low" == k && (ga = xa);
                                fa = C
                            }
                            Z.push(Bb);
                            Z.push(Ab);
                            Z.push(Cb);
                            ca = C;
                            ja = B
                        }
                }
                if (!ba && !isNaN(U)) {
                    var Pb = this.hideBulletsCount;
                    if (this.end - this.start <= Pb || 0 === Pb) {
                        var Ja = this.createBullet(y,
                            fa, ga, J);
                        Ja || (Ja = 0);
                        var Qb = this.labelText;
                        if (Qb) {
                            var ha = this.createLabel(y, 0, 0, Qb), Ka = 0, La = 0, Rb = ha.getBBox(), Qa = Rb.width, Ma = Rb.height;
                            switch (N) {
                                case "left":
                                    Ka = -(Qa / 2 + Ja / 2 + 3);
                                    break;
                                case "top":
                                    La = -(Ma / 2 + Ja / 2 + 3);
                                    break;
                                case "right":
                                    Ka = Ja / 2 + 2 + Qa / 2;
                                    break;
                                case "bottom":
                                    q && "column" == g ? (ca = na, 0 > U || 0 < U && f.reversed ? (Ka = -6, ha.attr({"text-anchor": "end"})) : (Ka = 6, ha.attr({"text-anchor": "start"}))) : (La = Ja / 2 + Ma / 2, ha.x = -(Qa / 2 + 2));
                                    break;
                                case "middle":
                                    "column" == g && (q ? (La = -(Ma / 2) + this.fontSize / 2, Ka = -(C - M) / 2 - F, Math.abs(C -
                                    M) < Qa && !this.showAllValueLabels && (ha.remove(), ha = null)) : (La = -(B - K) / 2 - G, Math.abs(B - K) < Ma && !this.showAllValueLabels && (ha.remove(), ha = null)));
                                    break;
                                case "inside":
                                    q ? (La = -(Ma / 2) + this.fontSize / 2, Ka = 0 > ea ? Qa / 2 + 6 : -Qa / 2 - 6) : La = 0 > ea ? Ma : -Ma
                            }
                            if (ha) {
                                if (isNaN(ja) || isNaN(ca))ha.remove(), ha = null; else if (ca += Ka, ja += La, ha.translate(ca, ja), q) {
                                    if (0 > ja || ja > n)ha.remove(), ha = null
                                } else {
                                    var Sb = 0;
                                    "3d" == V && (Sb = F * L);
                                    if (0 > ca || ca > m + Sb)ha.remove(), ha = null
                                }
                                ha && this.allBullets.push(ha)
                            }
                        }
                        if ("regular" == V || "100%" == V) {
                            var Tb = f.totalText;
                            if (Tb) {
                                var Ra = this.createLabel(y, 0, 0, Tb, f.totalTextColor);
                                this.allBullets.push(Ra);
                                var Ub = Ra.getBBox(), Vb = Ub.width, Wb = Ub.height, Za, $a, Xb = f.totals[J];
                                Xb && Xb.remove();
                                var ab = 0;
                                "column" != g && (ab = Ja);
                                q ? ($a = B, Za = 0 > U ? C - Vb / 2 - 2 - ab : C + Vb / 2 + 3 + ab) : (Za = C, $a = 0 > U ? B + Wb / 2 + ab : B - Wb / 2 - 3 - ab);
                                Ra.translate(Za, $a);
                                f.totals[J] = Ra;
                                q ? (0 > $a || $a > n) && Ra.remove() : (0 > Za || Za > m) && Ra.remove()
                            }
                        }
                    }
                }
            }
        }
        if ("line" == g || "step" == g || "smoothedLine" == g)"smoothedLine" == g ? this.drawSmoothedGraph(v, t, O, P) : this.drawLineGraph(v, t, O, P), ba || this.launchAnimation();
        this.bulletsHidden && this.hideBullets()
    }, animateColumns: function (a, b, c, d, e, f) {
        var g = this;
        c = g.chart.startDuration;
        0 < c && !g.animationPlayed && (g.seqAn ? (a.set.hide(), g.animationArray.push(a), a = setTimeout(function () {
            g.animate.call(g)
        }, c / (g.end - g.start + 1) * (b - g.start) * 1E3), g.timeOuts.push(a)) : g.animate(a))
    }, createLabel: function (a, b, c, d, e) {
        var f = this.chart, g = a.labelColor;
        g || (g = this.color);
        g || (g = f.color);
        e && (g = e);
        e = this.fontSize;
        void 0 === e && (this.fontSize = e = f.fontSize);
        var h = this.labelFunction;
        d = f.formatString(d,
            a);
        d = AmCharts.cleanFromEmpty(d);
        h && (d = h(a, d));
        a = AmCharts.text(this.container, d, g, f.fontFamily, e);
        a.node.style.pointerEvents = "none";
        a.translate(b, c);
        this.bulletSet.push(a);
        return a
    }, positiveClip: function (a) {
        a.clipRect(this.pmx, this.pmy, this.pmw, this.pmh)
    }, negativeClip: function (a) {
        a.clipRect(this.nmx, this.nmy, this.nmw, this.nmh)
    }, drawLineGraph: function (a, b, c, d) {
        var e = this;
        if (1 < a.length) {
            var f = e.set, g = e.chart, h = e.container, k = h.set(), l = h.set();
            f.push(l);
            f.push(k);
            var m = e.lineAlpha, n = e.lineThickness, f =
                e.fillAlphas, p = e.lineColorR, q = e.negativeLineAlpha;
            isNaN(q) && (q = m);
            var r = e.lineColorSwitched;
            r && (p = r);
            var r = e.fillColorsR, s = e.fillColorsSwitched;
            s && (r = s);
            var w = e.dashLength;
            (s = e.dashLengthSwitched) && (w = s);
            var s = e.negativeLineColor, v = e.negativeFillColors, t = e.negativeFillAlphas, u = e.baseCoord;
            0 !== e.negativeBase && (u = e.valueAxis.getCoordinate(e.negativeBase));
            m = AmCharts.line(h, a, b, p, m, n, w, !1, !0);
            k.push(m);
            k.click(function (a) {
                e.handleGraphEvent(a, "clickGraph")
            }).mouseover(function (a) {
                e.handleGraphEvent(a,
                    "rollOverGraph")
            }).mouseout(function (a) {
                e.handleGraphEvent(a, "rollOutGraph")
            });
            void 0 === s || e.useNegativeColorIfDown || (n = AmCharts.line(h, a, b, s, q, n, w, !1, !0), l.push(n));
            if (0 < f || 0 < t)if (n = a.join(";").split(";"), q = b.join(";").split(";"), m = g.type, "serial" == m ? 0 < c.length ? (c.reverse(), d.reverse(), n = a.concat(c), q = b.concat(d)) : e.rotate ? (q.push(q[q.length - 1]), n.push(u), q.push(q[0]), n.push(u), q.push(q[0]), n.push(n[0])) : (n.push(n[n.length - 1]), q.push(u), n.push(n[0]), q.push(u), n.push(a[0]), q.push(q[0])) : "xy" == m &&
                (b = e.fillToAxis) && (AmCharts.isString(b) && (b = g.getValueAxisById(b)), "H" == b.orientation ? (u = "top" == b.position ? 0 : b.viH, n.push(n[n.length - 1]), q.push(u), n.push(n[0]), q.push(u), n.push(a[0]), q.push(q[0])) : (u = "left" == b.position ? 0 : b.viW, q.push(q[q.length - 1]), n.push(u), q.push(q[0]), n.push(u), q.push(q[0]), n.push(n[0]))), a = e.gradientRotation, 0 < f && (g = AmCharts.polygon(h, n, q, r, f, 1, "#000", 0, a), g.pattern(e.pattern), k.push(g)), v || void 0 !== s)isNaN(t) && (t = f), v || (v = s), h = AmCharts.polygon(h, n, q, v, t, 1, "#000", 0, a), h.pattern(e.pattern),
                l.push(h), l.click(function (a) {
                e.handleGraphEvent(a, "clickGraph")
            }).mouseover(function (a) {
                e.handleGraphEvent(a, "rollOverGraph")
            }).mouseout(function (a) {
                e.handleGraphEvent(a, "rollOutGraph")
            });
            e.applyMask(l, k)
        }
    }, applyMask: function (a, b) {
        var c = a.length();
        "serial" != this.chart.type || this.scrollbar || (this.positiveClip(b), 0 < c && this.negativeClip(a))
    }, drawSmoothedGraph: function (a, b, c, d) {
        if (1 < a.length) {
            var e = this.set, f = this.container, g = f.set(), h = f.set();
            e.push(h);
            e.push(g);
            var k = this.lineAlpha, l = this.lineThickness,
                e = this.dashLength, m = this.fillAlphas, n = this.lineColorR, p = this.fillColorsR, q = this.negativeLineColor, r = this.negativeFillColors, s = this.negativeFillAlphas, w = this.baseCoord, v = this.lineColorSwitched;
            v && (n = v);
            (v = this.fillColorsSwitched) && (p = v);
            v = this.negativeLineAlpha;
            isNaN(v) && (v = k);
            k = new AmCharts.Bezier(f, a, b, n, k, l, p, 0, e);
            g.push(k.path);
            void 0 !== q && (l = new AmCharts.Bezier(f, a, b, q, v, l, p, 0, e), h.push(l.path));
            0 < m && (k = a.join(";").split(";"), n = b.join(";").split(";"), l = "", 0 < c.length ? (c.push("M"), d.push("M"), c.reverse(),
                d.reverse(), k = a.concat(c), n = b.concat(d)) : (this.rotate ? (l += " L" + w + "," + b[b.length - 1], l += " L" + w + "," + b[0]) : (l += " L" + a[a.length - 1] + "," + w, l += " L" + a[0] + "," + w), l += " L" + a[0] + "," + b[0]), c = new AmCharts.Bezier(f, k, n, NaN, 0, 0, p, m, e, l), c.path.pattern(this.pattern), g.push(c.path), r || void 0 !== q) && (s || (s = m), r || (r = q), a = new AmCharts.Bezier(f, a, b, NaN, 0, 0, r, s, e, l), a.path.pattern(this.pattern), h.push(a.path));
            this.applyMask(h, g)
        }
    }, launchAnimation: function () {
        var a = this, b = a.chart.startDuration;
        if (0 < b && !a.animationPlayed) {
            var c =
                a.set, d = a.bulletSet;
            AmCharts.VML || (c.attr({opacity: a.startAlpha}), d.attr({opacity: a.startAlpha}));
            c.hide();
            d.hide();
            a.seqAn ? (b = setTimeout(function () {
                a.animateGraphs.call(a)
            }, a.index * b * 1E3), a.timeOuts.push(b)) : a.animateGraphs()
        }
    }, animateGraphs: function () {
        var a = this.chart, b = this.set, c = this.bulletSet, d = this.x, e = this.y;
        b.show();
        c.show();
        var f = a.startDuration, a = a.startEffect;
        b && (this.rotate ? (b.translate(-1E3, e), c.translate(-1E3, e)) : (b.translate(d, -1E3), c.translate(d, -1E3)), b.animate({
            opacity: 1, translate: d +
            "," + e
        }, f, a), c.animate({opacity: 1, translate: d + "," + e}, f, a))
    }, animate: function (a) {
        var b = this.chart, c = this.animationArray;
        !a && 0 < c.length && (a = c[0], c.shift());
        c = AmCharts[AmCharts.getEffect(b.startEffect)];
        b = b.startDuration;
        a && (this.rotate ? a.animateWidth(b, c) : a.animateHeight(b, c), a.set.show())
    }, legendKeyColor: function () {
        var a = this.legendColor, b = this.lineAlpha;
        void 0 === a && (a = this.lineColorR, 0 === b && (b = this.fillColorsR) && (a = "object" == typeof b ? b[0] : b));
        return a
    }, legendKeyAlpha: function () {
        var a = this.legendAlpha;
        void 0 === a && (a = this.lineAlpha, this.fillAlphas > a && (a = this.fillAlphas), 0 === a && (a = this.bulletAlpha), 0 === a && (a = 1));
        return a
    }, createBullet: function (a, b, c, d) {
        if (!isNaN(b) && !isNaN(c)) {
            d = this.container;
            var e = this.bulletOffset, f = this.bulletSize;
            isNaN(a.bulletSize) || (f = a.bulletSize);
            var g = a.values.value, h = this.maxValue, k = this.minValue, l = this.maxBulletSize, m = this.minBulletSize;
            isNaN(h) || (isNaN(g) || (f = (g - k) / (h - k) * (l - m) + m), k == h && (f = l));
            h = f;
            this.bulletAxis && (f = a.values.error, isNaN(f) || (g = f), f = this.bulletAxis.stepWidth *
            g);
            f < this.minBulletSize && (f = this.minBulletSize);
            this.rotate ? b = a.isNegative ? b - e : b + e : c = a.isNegative ? c + e : c - e;
            var n, m = this.bulletColorR;
            a.lineColor && (this.bulletColorSwitched = a.lineColor);
            this.bulletColorSwitched && (m = this.bulletColorSwitched);
            a.isNegative && void 0 !== this.bulletColorNegative && (m = this.bulletColorNegative);
            void 0 !== a.color && (m = a.color);
            var p;
            "xy" == this.chart.type && this.valueField && (p = this.pattern, a.pattern && (p = a.pattern));
            e = this.bullet;
            a.bullet && (e = a.bullet);
            var g = this.bulletBorderThickness,
                k = this.bulletBorderColorR, l = this.bulletBorderAlpha, q = this.bulletAlpha;
            k || (k = m);
            this.useLineColorForBulletBorder && (k = this.lineColorR);
            var r = a.alpha;
            isNaN(r) || (q = r);
            if ("none" != this.bullet || a.bullet)n = AmCharts.bullet(d, e, f, m, q, g, k, l, h, 0, p);
            if (this.customBullet || a.customBullet)p = this.customBullet, a.customBullet && (p = a.customBullet), p && (n && n.remove(), "function" == typeof p ? (n = new p, n.chart = this.chart, a.bulletConfig && (n.availableSpace = c, n.graph = this, n.graphDataItem = a, n.bulletY = c, a.bulletConfig.minCoord = this.minCoord -
            c, n.bulletConfig = a.bulletConfig), n.write(d), n = n.set) : (this.chart.path && (p = this.chart.path + p), n = d.set(), d = d.image(p, 0, 0, f, f), n.push(d), this.centerCustomBullets && d.translate(-f / 2, -f / 2)));
            n && ((a.url || this.showHandOnHover) && n.setAttr("cursor", "pointer"), "serial" == this.chart.type && (0 > b - 0 || b - 0 > this.width || c < -f / 2 || c - 0 > this.height) && (n.remove(), n = null), n && (this.bulletSet.push(n), n.translate(b, c), this.addListeners(n, a), this.allBullets.push(n)), a.bx = b, a.by = c);
            a.bulletGraphics = n;
            return f
        }
    }, showBullets: function () {
        var a =
            this.allBullets, b;
        this.bulletsHidden = !1;
        for (b = 0; b < a.length; b++)a[b].show()
    }, hideBullets: function () {
        var a = this.allBullets, b;
        this.bulletsHidden = !0;
        for (b = 0; b < a.length; b++)a[b].hide()
    }, addListeners: function (a, b) {
        var c = this;
        a.mouseover(function (a) {
            c.handleRollOver(b, a)
        }).mouseout(function (a) {
            c.handleRollOut(b, a)
        }).touchend(function (a) {
            c.handleRollOver(b, a);
            c.chart.panEventsEnabled && c.handleClick(b, a)
        }).touchstart(function (a) {
            c.handleRollOver(b, a)
        }).click(function (a) {
            c.handleClick(b, a)
        }).dblclick(function (a) {
            c.handleDoubleClick(b,
                a)
        }).contextmenu(function (a) {
            c.handleRightClick(b, a)
        })
    }, handleRollOver: function (a, b) {
        if (a) {
            var c = this.chart, d = {
                type: "rollOverGraphItem",
                item: a,
                index: a.index,
                graph: this,
                target: this,
                chart: this.chart,
                event: b
            };
            this.fire("rollOverGraphItem", d);
            c.fire("rollOverGraphItem", d);
            clearTimeout(c.hoverInt);
            d = this.showBalloon;
            c.chartCursor && "serial" == c.type && (d = !1, !c.chartCursor.valueBalloonsEnabled && this.showBalloon && (d = !0));
            if (d) {
                var d = c.formatString(this.balloonText, a, !0), e = this.balloonFunction;
                e && (d = e(a, a.graph));
                d = AmCharts.cleanFromEmpty(d);
                e = c.getBalloonColor(this, a);
                c.balloon.showBullet = !1;
                c.balloon.pointerOrientation = "V";
                var f = a.x, g = a.y;
                c.rotate && (f = a.y, g = a.x);
                c.showBalloon(d, e, !0, f + c.marginLeftReal, g + c.marginTopReal)
            }
        }
        this.handleGraphEvent(b, "rollOverGraph")
    }, handleRollOut: function (a, b) {
        this.chart.hideBalloon();
        if (a) {
            var c = {
                type: "rollOutGraphItem",
                item: a,
                index: a.index,
                graph: this,
                target: this,
                chart: this.chart,
                event: b
            };
            this.fire("rollOutGraphItem", c);
            this.chart.fire("rollOutGraphItem", c)
        }
        this.handleGraphEvent(b,
            "rollOutGraph")
    }, handleClick: function (a, b) {
        if (a) {
            var c = {
                type: "clickGraphItem",
                item: a,
                index: a.index,
                graph: this,
                target: this,
                chart: this.chart,
                event: b
            };
            this.fire("clickGraphItem", c);
            this.chart.fire("clickGraphItem", c);
            AmCharts.getURL(a.url, this.urlTarget)
        }
        this.handleGraphEvent(b, "clickGraph")
    }, handleGraphEvent: function (a, b) {
        var c = {type: b, graph: this, target: this, chart: this.chart, event: a};
        this.fire(b, c);
        this.chart.fire(b, c)
    }, handleRightClick: function (a, b) {
        if (a) {
            var c = {
                type: "rightClickGraphItem", item: a, index: a.index,
                graph: this, target: this, chart: this.chart, event: b
            };
            this.fire("rightClickGraphItem", c);
            this.chart.fire("rightClickGraphItem", c)
        }
    }, handleDoubleClick: function (a, b) {
        if (a) {
            var c = {
                type: "doubleClickGraphItem",
                item: a,
                index: a.index,
                graph: this,
                target: this,
                chart: this.chart,
                event: b
            };
            this.fire("doubleClickGraphItem", c);
            this.chart.fire("doubleClickGraphItem", c)
        }
    }, zoom: function (a, b) {
        this.start = a;
        this.end = b;
        this.draw()
    }, changeOpacity: function (a) {
        var b = this.set;
        b && b.setAttr("opacity", a);
        if (b = this.ownColumns) {
            var c;
            for (c = 0; c < b.length; c++) {
                var d = b[c].set;
                d && d.setAttr("opacity", a)
            }
        }
        (b = this.bulletSet) && b.setAttr("opacity", a)
    }, destroy: function () {
        AmCharts.remove(this.set);
        AmCharts.remove(this.bulletSet);
        var a = this.timeOuts;
        if (a) {
            var b;
            for (b = 0; b < a.length; b++)clearTimeout(a[b])
        }
        this.timeOuts = []
    }
});
AmCharts.ChartCursor = AmCharts.Class({
    construct: function (a) {
        this.cname = "ChartCursor";
        this.createEvents("changed", "zoomed", "onHideCursor", "draw", "selected", "moved");
        this.enabled = !0;
        this.cursorAlpha = 1;
        this.selectionAlpha = .2;
        this.cursorColor = "#CC0000";
        this.categoryBalloonAlpha = 1;
        this.color = "#FFFFFF";
        this.type = "cursor";
        this.zoomed = !1;
        this.zoomable = !0;
        this.pan = !1;
        this.categoryBalloonDateFormat = "MMM DD, YYYY";
        this.categoryBalloonEnabled = this.valueBalloonsEnabled = !0;
        this.rolledOver = !1;
        this.cursorPosition =
            "middle";
        this.bulletsEnabled = this.skipZoomDispatch = !1;
        this.bulletSize = 8;
        this.selectWithoutZooming = this.oneBalloonOnly = !1;
        this.graphBulletSize = 1.7;
        this.animationDuration = .3;
        this.zooming = !1;
        this.adjustment = 0;
        this.avoidBalloonOverlapping = !0;
        AmCharts.applyTheme(this, a, this.cname)
    }, draw: function () {
        var a = this;
        a.destroy();
        var b = a.chart, c = b.container;
        a.rotate = b.rotate;
        a.container = c;
        c = c.set();
        c.translate(a.x, a.y);
        a.set = c;
        b.cursorSet.push(c);
        c = new AmCharts.AmBalloon;
        c.chart = b;
        a.categoryBalloon = c;
        AmCharts.copyProperties(b.balloon,
            c);
        c.cornerRadius = 0;
        c.shadowAlpha = 0;
        c.borderThickness = 1;
        c.borderAlpha = 1;
        c.showBullet = !1;
        var d = a.categoryBalloonColor;
        void 0 === d && (d = a.cursorColor);
        c.fillColor = d;
        c.fillAlpha = a.categoryBalloonAlpha;
        c.borderColor = d;
        c.color = a.color;
        d = a.valueLineAxis;
        AmCharts.isString(d) && (d = b.getValueAxisById(d));
        d || (d = b.valueAxes[0]);
        a.valueLineAxis = d;
        a.valueLineBalloonEnabled && (d = new AmCharts.AmBalloon, a.vaBalloon = d, AmCharts.copyProperties(c, d), d.animationDuration = 0, a.rotate || (d.pointerOrientation = "H"));
        a.rotate && (c.pointerOrientation =
            "H");
        a.extraWidth = 0;
        a.prevX = [];
        a.prevY = [];
        a.prevTX = [];
        a.prevTY = [];
        if (a.valueBalloonsEnabled)for (c = 0; c < b.graphs.length; c++)d = new AmCharts.AmBalloon, d.chart = b, AmCharts.copyProperties(b.balloon, d), b.graphs[c].valueBalloon = d;
        "cursor" == a.type ? a.createCursor() : a.createCrosshair();
        a.interval = setInterval(function () {
            a.detectMovement.call(a)
        }, 40)
    }, updateData: function () {
        var a = this.chart;
        this.data = a.chartData;
        this.firstTime = a.firstTime;
        this.lastTime = a.lastTime
    }, createCursor: function () {
        var a = this.chart, b = this.cursorAlpha,
            c = a.categoryAxis, d = this.categoryBalloon, e, f, g, h;
        g = a.dx;
        h = a.dy;
        var k = this.width, l = this.height, a = a.rotate;
        d.pointerWidth = c.tickLength;
        a ? (e = [0, k, k + g], f = [0, 0, h], g = [g, 0, 0], h = [h, 0, l]) : (e = [g, 0, 0], f = [h, 0, l], g = [0, k, k + g], h = [0, 0, h]);
        this.line = e = AmCharts.line(this.container, e, f, this.cursorColor, b, 1);
        (f = this.fullRectSet) ? (f.push(e), f.translate(this.x, this.y)) : this.set.push(e);
        this.valueLineEnabled && (e = this.valueLineAlpha, isNaN(e) || (b = e), this.vLine = b = AmCharts.line(this.container, g, h, this.cursorColor, b, 1), this.set.push(b));
        this.setBalloonBounds(d, c, a);
        (c = this.vaBalloon) && this.setBalloonBounds(c, this.valueLineAxis, !a);
        this.hideCursor()
    }, createCrosshair: function () {
        var a = this.cursorAlpha, b = this.container, c = AmCharts.line(b, [0, 0], [0, this.height], this.cursorColor, a, 1), a = AmCharts.line(b, [0, this.width], [0, 0], this.cursorColor, a, 1);
        this.set.push(c);
        this.set.push(a);
        this.vLine = c;
        this.hLine = a;
        this.hideCursor()
    }, detectMovement: function () {
        var a = this.chart;
        if (a.mouseIsOver) {
            var b = a.mouseX - this.x, c = a.mouseY - this.y;
            -.5 < b && b < this.width +
            1 && 0 < c && c < this.height ? (this.drawing ? this.rolledOver || a.setMouseCursor("crosshair") : this.pan && (this.rolledOver || a.setMouseCursor("move")), this.rolledOver = !0, (this.valueLineEnabled || this.valueLineBalloonEnabled) && this.updateVLine(b, c), this.setPosition()) : this.rolledOver && (this.handleMouseOut(), this.rolledOver = !1)
        } else this.rolledOver && (this.handleMouseOut(), this.rolledOver = !1)
    }, updateVLine: function (a, b) {
        var c = this.vLine, d = this.vaBalloon;
        if ((c || d) && !this.panning && !this.drawing) {
            c && c.show();
            var e = this.valueLineAxis,
                f, g = this.rotate;
            g ? (c && c.translate(a, 0), e && (f = e.coordinateToValue(a)), c = a) : (c && c.translate(0, b), e && (f = e.coordinateToValue(b)), c = b - 1);
            if (d && !isNaN(f) && this.prevLineValue != f) {
                var h = e.formatValue(f, !0);
                d && (this.setBalloonPosition(d, e, c, !g), d.showBalloon(h))
            }
            this.prevLineValue = f
        }
    }, getMousePosition: function () {
        var a, b = this.width, c = this.height;
        a = this.chart;
        this.rotate ? (a = a.mouseY - this.y, 0 > a && (a = 0), a > c && (a = c)) : (a = a.mouseX - this.x - 1, 0 > a && (a = 0), a > b && (a = b));
        return a
    }, updateCrosshair: function () {
        var a = this.chart,
            b = a.mouseX - this.x, c = a.mouseY - this.y, d = this.vLine, e = this.hLine, b = AmCharts.fitToBounds(b, 0, this.width), c = AmCharts.fitToBounds(c, 0, this.height);
        0 < this.cursorAlpha && (d.show(), e.show(), d.translate(b, 0), e.translate(0, c));
        this.zooming && (a.hideXScrollbar && (b = NaN), a.hideYScrollbar && (c = NaN), this.updateSelectionSize(b, c));
        this.fireMoved();
        a.mouseIsOver || this.zooming || this.hideCursor()
    }, fireMoved: function () {
        var a = this.chart, b = {type: "moved", target: this};
        b.chart = a;
        b.zooming = this.zooming;
        b.x = a.mouseX - this.x;
        b.y =
            a.mouseY - this.y;
        this.fire("moved", b)
    }, updateSelectionSize: function (a, b) {
        AmCharts.remove(this.selection);
        var c = this.selectionPosX, d = this.selectionPosY, e = 0, f = 0, g = this.width, h = this.height;
        isNaN(a) || (c > a && (e = a, g = c - a), c < a && (e = c, g = a - c), c == a && (e = a, g = 0), g += this.extraWidth, e -= this.extraWidth / 2);
        isNaN(b) || (d > b && (f = b, h = d - b), d < b && (f = d, h = b - d), d == b && (f = b, h = 0), h += this.extraWidth, f -= this.extraWidth / 2);
        0 < g && 0 < h && (c = AmCharts.rect(this.container, g, h, this.cursorColor, this.selectionAlpha), c.translate(e + this.x, f + this.y),
            this.selection = c)
    }, arrangeBalloons: function () {
        var a = this.valueBalloons, b = this.x, c = this.y, d = this.height + c;
        a.sort(this.compareY);
        var e;
        for (e = 0; e < a.length; e++) {
            var f = a[e].balloon;
            f.setBounds(b, c, b + this.width, d);
            f.prevX = this.prevX[e];
            f.prevY = this.prevY[e];
            f.prevTX = this.prevTX[e];
            f.prevTY = this.prevTY[e];
            f.draw();
            d = f.yPos - 3
        }
        this.arrangeBalloons2()
    }, compareY: function (a, b) {
        return a.yy < b.yy ? 1 : -1
    }, arrangeBalloons2: function () {
        var a = this.valueBalloons;
        a.reverse();
        var b, c = this.x, d, e, f = a.length;
        for (e = 0; e < f; e++) {
            var g =
                a[e].balloon;
            b = g.bottom;
            var h = g.bottom - g.yPos, k = f - e - 1;
            0 < e && b - h < d + 3 && (g.setBounds(c, d + 3, c + this.width, d + h + 3), g.prevX = this.prevX[k], g.prevY = this.prevY[k], g.prevTX = this.prevTX[k], g.prevTY = this.prevTY[k], g.draw());
            g.set && g.set.show();
            this.prevX[k] = g.prevX;
            this.prevY[k] = g.prevY;
            this.prevTX[k] = g.prevTX;
            this.prevTY[k] = g.prevTY;
            d = g.bottom
        }
    }, showBullets: function () {
        AmCharts.remove(this.allBullets);
        var a = this.container, b = a.set();
        this.set.push(b);
        this.set.show();
        this.allBullets = b;
        var b = this.chart.graphs, c;
        for (c =
                 0; c < b.length; c++) {
            var d = b[c];
            if (!d.hidden && d.balloonText) {
                var e = this.data[this.index].axes[d.valueAxis.id].graphs[d.id], f = e.y;
                if (!isNaN(f)) {
                    var g, h;
                    g = e.x;
                    this.rotate ? (h = f, f = g) : h = g;
                    d = AmCharts.circle(a, this.bulletSize / 2, this.chart.getBalloonColor(d, e, !0), d.cursorBulletAlpha);
                    d.translate(h, f);
                    this.allBullets.push(d)
                }
            }
        }
    }, destroy: function () {
        this.clear();
        AmCharts.remove(this.selection);
        this.selection = null;
        var a = this.categoryBalloon;
        a && a.destroy();
        (a = this.vaBalloon) && a.destroy();
        this.destroyValueBalloons();
        AmCharts.remove(this.set)
    }, clear: function () {
        clearInterval(this.interval)
    }, destroyValueBalloons: function () {
        var a = this.valueBalloons;
        if (a) {
            var b;
            for (b = 0; b < a.length; b++)a[b].balloon.hide()
        }
    }, zoom: function (a, b, c, d) {
        var e = this.chart;
        this.destroyValueBalloons();
        this.zooming = !1;
        var f;
        this.rotate ? this.selectionPosY = f = e.mouseY : this.selectionPosX = f = e.mouseX;
        this.start = a;
        this.end = b;
        this.startTime = c;
        this.endTime = d;
        this.zoomed = !0;
        d = e.categoryAxis;
        e = this.rotate;
        b = this.width;
        c = this.height;
        a = d.stepWidth;
        this.fullWidth &&
        (f = 1, d.parseDates && !d.equalSpacing && (f = d.minDuration()), e ? this.extraWidth = c = a * f : (this.extraWidth = b = a * f, this.categoryBalloon.minWidth = b), this.line && this.line.remove(), this.line = AmCharts.rect(this.container, b, c, this.cursorColor, this.cursorAlpha, 0), this.fullRectSet && this.fullRectSet.push(this.line));
        this.stepWidth = a;
        this.tempVal = this.valueBalloonsEnabled;
        this.valueBalloonsEnabled = !1;
        this.setPosition();
        this.valueBalloonsEnabled = this.tempVal;
        this.hideCursor()
    }, hideObj: function (a) {
        a && a.hide()
    }, hideCursor: function (a) {
        void 0 ===
        a && (a = !0);
        this.hideObj(this.set);
        this.hideObj(this.categoryBalloon);
        this.hideObj(this.line);
        this.hideObj(this.vLine);
        this.hideObj(this.hLine);
        this.hideObj(this.vaBalloon);
        this.hideObj(this.allBullets);
        this.destroyValueBalloons();
        this.selectWithoutZooming || AmCharts.remove(this.selection);
        this.previousIndex = NaN;
        a && this.fire("onHideCursor", {type: "onHideCursor", chart: this.chart, target: this});
        this.drawing || this.chart.setMouseCursor("auto");
        this.normalizeBulletSize()
    }, setPosition: function (a, b, c) {
        void 0 ===
        b && (b = !0);
        if ("cursor" == this.type) {
            if (this.tempPosition = NaN, AmCharts.ifArray(this.data))isNaN(a) && (a = this.getMousePosition()), (a != this.previousMousePosition || !0 === this.zoomed || this.oneBalloonOnly) && !isNaN(a) && ("mouse" == this.cursorPosition && (this.tempPosition = a), isNaN(c) && (c = this.chart.categoryAxis.xToIndex(a)), c != this.previousIndex || this.zoomed || "mouse" == this.cursorPosition || this.oneBalloonOnly) && (this.updateCursor(c, b), this.zoomed = !1), this.previousMousePosition = a
        } else this.updateCrosshair()
    }, normalizeBulletSize: function () {
        var a =
            this.resizedBullets;
        if (a)for (var b = 0; b < a.length; b++) {
            var c = a[b], d = c.bulletGraphics;
            d && (d.translate(c.bx, c.by, 1), c = c.graph, isNaN(this.graphBulletAlpha) || (d.setAttr("fill-opacity", c.bulletAlpha), d.setAttr("stroke-opacity", c.bulletBorderAlpha)))
        }
    }, updateCursor: function (a, b) {
        var c = this.chart, d = this.fullWidth, e = c.mouseX - this.x, f = c.mouseY - this.y;
        this.drawingNow && (AmCharts.remove(this.drawingLine), this.drawingLine = AmCharts.line(this.container, [this.x + this.drawStartX, this.x + e], [this.y + this.drawStartY, this.y +
        f], this.cursorColor, 1, 1));
        if (this.enabled) {
            void 0 === b && (b = !0);
            this.index = a += this.adjustment;
            var g = c.categoryAxis, h = c.dx, k = c.dy, l = this.x + 1, m = this.y + 1, n = this.width, p = this.height, q = this.data[a];
            this.fireMoved();
            if (q) {
                var r = q.x[g.id], s = c.rotate, w = this.stepWidth, v = this.categoryBalloon, t = this.firstTime, u = this.lastTime, x = this.cursorPosition, E = this.zooming, A = this.panning, z = c.graphs;
                if (c.mouseIsOver || E || A || this.forceShow)if (this.forceShow = !1, A) {
                    var h = this.panClickPos, c = this.panClickEndTime, E = this.panClickStartTime,
                        H = this.panClickEnd, l = this.panClickStart, e = (s ? h - f : h - e) / w;
                    if (!g.parseDates || g.equalSpacing)e = Math.round(e);
                    0 !== e && (h = {
                        type: "zoomed",
                        target: this
                    }, h.chart = this.chart, g.parseDates && !g.equalSpacing ? (c + e > u && (e = u - c), E + e < t && (e = t - E), h.start = Math.round(E + e), h.end = Math.round(c + e), this.fire(h.type, h)) : H + e >= this.data.length || 0 > l + e || (h.start = l + e, h.end = H + e, this.fire(h.type, h)))
                } else {
                    "start" == x ? r -= g.cellWidth / 2 : "mouse" == x && (c.mouseIsOver ? r = s ? f - 2 : e - 2 : isNaN(this.tempPosition) || (r = this.tempPosition - 2));
                    if (s) {
                        if (0 > r)if (E)r =
                            0; else {
                            this.hideCursor();
                            return
                        }
                        if (r > p + 1)if (E)r = p + 1; else {
                            this.hideCursor();
                            return
                        }
                    } else {
                        if (0 > r)if (E)r = 0; else {
                            this.hideCursor();
                            return
                        }
                        if (r > n)if (E)r = n; else {
                            this.hideCursor();
                            return
                        }
                    }
                    if (0 < this.cursorAlpha) {
                        var F = this.line;
                        s ? (t = 0, u = r + k, d && (u -= g.cellWidth / 2)) : (t = r, u = 0, d && (t -= g.cellWidth / 2));
                        w = this.animationDuration;
                        0 < w && !this.zooming ? isNaN(this.previousX) ? F.translate(t, u) : (F.translate(this.previousX, this.previousY), F.animate({translate: t + "," + u}, w, "easeOutSine")) : F.translate(t, u);
                        this.previousX = t;
                        this.previousY =
                            u;
                        F.show()
                    }
                    this.linePos = s ? r + k : r;
                    E && (d && F.hide(), s ? this.updateSelectionSize(NaN, r) : this.updateSelectionSize(r, NaN));
                    w = !0;
                    E && (w = !1);
                    this.categoryBalloonEnabled && w ? (this.setBalloonPosition(v, g, r, s), (t = this.categoryBalloonFunction) ? v.showBalloon(t(q.category)) : g.parseDates ? (g = AmCharts.formatDate(q.category, this.categoryBalloonDateFormat, c), -1 != g.indexOf("fff") && (g = AmCharts.formatMilliseconds(g, q.category)), v.showBalloon(g)) : v.showBalloon(AmCharts.fixNewLines(q.category))) : v.hide();
                    z && this.bulletsEnabled &&
                    this.showBullets();
                    if (this.oneBalloonOnly) {
                        r = Infinity;
                        for (g = 0; g < z.length; g++)t = z[g], t.showBalloon && !t.hidden && t.balloonText && (u = q.axes[t.valueAxis.id].graphs[t.id], v = u.y, isNaN(v) || (s ? Math.abs(e - v) < r && (r = Math.abs(e - v), H = t) : Math.abs(f - v) < r && (r = Math.abs(f - v), H = t)));
                        this.mostCloseGraph && (H = this.mostCloseGraph)
                    }
                    if (a != this.previousIndex || H != this.previousMostCloseGraph)if (this.normalizeBulletSize(), this.destroyValueBalloons(), this.resizedBullets = [], z && this.valueBalloonsEnabled && w && c.balloon.enabled) {
                        this.valueBalloons =
                            w = [];
                        for (g = 0; g < z.length; g++)if (t = z[g], v = NaN, (!this.oneBalloonOnly || t == H) && t.showBalloon && !t.hidden && t.balloonText && ("step" == t.type && "left" == t.stepDirection && (q = this.data[a + 1]), q)) {
                            if (u = q.axes[t.valueAxis.id].graphs[t.id])v = u.y;
                            if (this.showNextAvailable && isNaN(v) && a + 1 < this.data.length)for (r = a + 1; r < this.data.length; r++)if (d = this.data[r])if (u = d.axes[t.valueAxis.id].graphs[t.id], v = u.y, !isNaN(v))break;
                            if (!isNaN(v)) {
                                d = u.x;
                                k = !0;
                                if (s) {
                                    if (r = v, 0 > d || d > p)k = !1
                                } else if (r = d, d = v, 0 > r || r > n + h + 1)k = !1;
                                k && (k = this.graphBulletSize,
                                    F = this.graphBulletAlpha, 1 == k && isNaN(F) || !AmCharts.isModern || !(x = u.bulletGraphics) || (x.getBBox(), x.translate(u.bx, u.by, k), this.resizedBullets.push(u), isNaN(F) || (x.setAttr("fill-opacity", F), x.setAttr("stroke-opacity", F))), k = t.valueBalloon, F = c.getBalloonColor(t, u), k.setBounds(l, m, l + n, m + p), k.pointerOrientation = "H", x = this.balloonPointerOrientation, "vertical" == x && (k.pointerOrientation = "V"), "horizontal" == x && (k.pointerOrientation = "H"), k.changeColor(F), void 0 !== t.balloonAlpha && (k.fillAlpha = t.balloonAlpha),
                                void 0 !== t.balloonTextColor && (k.color = t.balloonTextColor), k.setPosition(r + l, d + m), r = c.formatString(t.balloonText, u, !0), (d = t.balloonFunction) && (r = d(u, t).toString()), "" !== r && (s ? k.showBalloon(r) : (k.text = r, k.show = !0), w.push({
                                    yy: v,
                                    balloon: k
                                })), !s && k.set && (k.set.hide(), t = k.textDiv) && (t.style.visibility = "hidden"))
                            }
                        }
                        this.avoidBalloonOverlapping && this.arrangeBalloons()
                    }
                    b ? (h = {type: "changed"}, h.index = a, h.chart = this.chart, h.zooming = E, h.mostCloseGraph = H, h.position = s ? f : e, h.target = this, c.fire("changed", h), this.fire("changed",
                        h), this.skipZoomDispatch = !1) : (this.skipZoomDispatch = !0, c.updateLegendValues(a));
                    this.previousIndex = a;
                    this.previousMostCloseGraph = H
                }
            }
        } else this.hideCursor()
    }, setBalloonPosition: function (a, b, c, d) {
        var e = b.position, f = b.inside;
        b = b.axisThickness;
        var g = this.chart, h = g.dx, g = g.dy, k = this.x, l = this.y, m = this.width, n = this.height;
        d ? (f && ("right" == e ? a.setBounds(k, l + g, k + m + h, l + c + g) : a.setBounds(k, l + g, k + m + h, l + c)), "right" == e ? f ? a.setPosition(k + m + h, l + c + g) : a.setPosition(k + m + h + b, l + c + g) : f ? a.setPosition(k, l + c) : a.setPosition(k -
        b, l + c)) : "top" == e ? f ? a.setPosition(k + c + h, l + g) : a.setPosition(k + c + h, l + g - b + 1) : f ? a.setPosition(k + c, l + n) : a.setPosition(k + c, l + n + b - 1)
    }, setBalloonBounds: function (a, b, c) {
        var d = b.position, e = b.inside, f = b.axisThickness, g = b.tickLength, h = this.chart, k = h.dx, h = h.dy, l = this.x, m = this.y, n = this.width, p = this.height;
        c ? (e && (a.pointerWidth = 0), "right" == d ? e ? a.setBounds(l, m + h, l + n + k, m + p + h) : a.setBounds(l + n + k + f, m + h, l + n + 1E3, m + p + h) : e ? a.setBounds(l, m, n + l, p + m) : a.setBounds(-1E3, -1E3, l - g - f, m + p + 15)) : (a.maxWidth = n, b.parseDates && (g = 0, a.pointerWidth =
            0), "top" == d ? e ? a.setBounds(l + k, m + h, n + k + l, p + m) : a.setBounds(l + k, -1E3, n + k + l, m + h - g - f) : e ? a.setBounds(l, m, n + l, p + m - g) : a.setBounds(l, m + p + g + f - 1, l + n, m + p + g + f))
    }, enableDrawing: function (a) {
        this.enabled = !a;
        this.hideCursor();
        this.rolledOver = !1;
        this.drawing = a
    }, isZooming: function (a) {
        a && a != this.zooming && this.handleMouseDown("fake");
        a || a == this.zooming || this.handleMouseUp()
    }, handleMouseOut: function () {
        if (this.enabled)if (this.zooming)this.setPosition(); else {
            this.index = void 0;
            var a = {type: "changed", index: void 0, target: this};
            a.chart = this.chart;
            this.fire("changed", a);
            this.hideCursor()
        }
    }, handleReleaseOutside: function () {
        this.handleMouseUp()
    }, handleMouseUp: function () {
        var a = this.chart, b = this.data, c;
        if (a) {
            var d = a.mouseX - this.x, e = a.mouseY - this.y;
            if (this.drawingNow) {
                this.drawingNow = !1;
                AmCharts.remove(this.drawingLine);
                c = this.drawStartX;
                var f = this.drawStartY;
                if (2 < Math.abs(c - d) || 2 < Math.abs(f - e))c = {
                    type: "draw",
                    target: this,
                    chart: a,
                    initialX: c,
                    initialY: f,
                    finalX: d,
                    finalY: e
                }, this.fire(c.type, c)
            }
            if (this.enabled && 0 < b.length) {
                if (this.pan)this.rolledOver = !1; else if (this.zoomable && this.zooming) {
                    c = this.selectWithoutZooming ? {type: "selected"} : {type: "zoomed"};
                    c.target = this;
                    c.chart = a;
                    if ("cursor" == this.type)this.rotate ? this.selectionPosY = e : this.selectionPosX = e = d, 2 > Math.abs(e - this.initialMouse) && this.fromIndex == this.index || (this.index < this.fromIndex ? (c.end = this.fromIndex, c.start = this.index) : (c.end = this.index, c.start = this.fromIndex), e = a.categoryAxis, e.parseDates && !e.equalSpacing && (b[c.start] && (c.start = b[c.start].time), b[c.end] && (c.end = a.getEndTime(b[c.end].time))),
                    this.skipZoomDispatch || this.fire(c.type, c)); else {
                        var g = this.initialMouseX, h = this.initialMouseY;
                        3 > Math.abs(d - g) && 3 > Math.abs(e - h) || (b = Math.min(g, d), f = Math.min(h, e), d = Math.abs(g - d), e = Math.abs(h - e), a.hideXScrollbar && (b = 0, d = this.width), a.hideYScrollbar && (f = 0, e = this.height), c.selectionHeight = e, c.selectionWidth = d, c.selectionY = f, c.selectionX = b, this.skipZoomDispatch || this.fire(c.type, c))
                    }
                    this.selectWithoutZooming || AmCharts.remove(this.selection)
                }
                this.skipZoomDispatch = !1
            }
        }
        this.panning = this.zooming = !1
    }, showCursorAt: function (a) {
        var b =
            this.chart.categoryAxis;
        a = b.parseDates ? b.dateToCoordinate(a) : b.categoryToCoordinate(a);
        this.previousMousePosition = NaN;
        this.forceShow = !0;
        this.setPosition(a, !1)
    }, clearSelection: function () {
        AmCharts.remove(this.selection)
    }, handleMouseDown: function (a) {
        if (this.zoomable || this.pan || this.drawing) {
            var b = this.rotate, c = this.chart, d = c.mouseX - this.x, e = c.mouseY - this.y;
            if (0 < d && d < this.width && 0 < e && e < this.height || "fake" == a)this.setPosition(), this.selectWithoutZooming && AmCharts.remove(this.selection), this.drawing ? (this.drawStartY =
                e, this.drawStartX = d, this.drawingNow = !0) : this.pan ? (this.zoomable = !1, c.setMouseCursor("move"), this.panning = !0, this.panClickPos = b ? e : d, this.panClickStart = this.start, this.panClickEnd = this.end, this.panClickStartTime = this.startTime, this.panClickEndTime = this.endTime) : this.zoomable && ("cursor" == this.type ? (this.fromIndex = this.index, b ? (this.initialMouse = e, this.selectionPosY = this.linePos) : (this.initialMouse = d, this.selectionPosX = this.linePos)) : (this.initialMouseX = d, this.initialMouseY = e, this.selectionPosX = d, this.selectionPosY =
                e), this.zooming = !0)
        }
    }
});
AmCharts.SimpleChartScrollbar = AmCharts.Class({
    construct: function (a) {
        this.createEvents("zoomed");
        this.backgroundColor = "#D4D4D4";
        this.backgroundAlpha = 1;
        this.selectedBackgroundColor = "#EFEFEF";
        this.scrollDuration = this.selectedBackgroundAlpha = 1;
        this.resizeEnabled = !0;
        this.hideResizeGrips = !1;
        this.scrollbarHeight = 20;
        this.updateOnReleaseOnly = !1;
        9 > document.documentMode && (this.updateOnReleaseOnly = !0);
        this.dragIconWidth = 18;
        this.dragIconHeight = 25;
        AmCharts.applyTheme(this, a, "SimpleChartScrollbar")
    }, draw: function () {
        var a =
            this;
        a.destroy();
        a.interval = setInterval(function () {
            a.updateScrollbar.call(a)
        }, 40);
        var b = a.chart.container, c = a.rotate, d = a.chart, e = b.set();
        a.set = e;
        d.scrollbarsSet.push(e);
        var f, g;
        c ? (f = a.scrollbarHeight, g = d.plotAreaHeight) : (g = a.scrollbarHeight, f = d.plotAreaWidth);
        a.width = f;
        if ((a.height = g) && f) {
            var h = AmCharts.rect(b, f, g, a.backgroundColor, a.backgroundAlpha, 1, a.backgroundColor, a.backgroundAlpha);
            a.bg = h;
            e.push(h);
            h = AmCharts.rect(b, f, g, "#000", .005);
            e.push(h);
            a.invisibleBg = h;
            h.click(function () {
                a.handleBgClick()
            }).mouseover(function () {
                a.handleMouseOver()
            }).mouseout(function () {
                a.handleMouseOut()
            }).touchend(function () {
                a.handleBgClick()
            });
            h = AmCharts.rect(b, f, g, a.selectedBackgroundColor, a.selectedBackgroundAlpha);
            a.selectedBG = h;
            e.push(h);
            f = AmCharts.rect(b, f, g, "#000", .005);
            a.dragger = f;
            e.push(f);
            f.mousedown(function (b) {
                a.handleDragStart(b)
            }).mouseup(function () {
                a.handleDragStop()
            }).mouseover(function () {
                a.handleDraggerOver()
            }).mouseout(function () {
                a.handleMouseOut()
            }).touchstart(function (b) {
                a.handleDragStart(b)
            }).touchend(function () {
                a.handleDragStop()
            });
            f = d.pathToImages;
            c ? (h = f + "dragIconH.gif", f = a.dragIconWidth, c = a.dragIconHeight) : (h = f + "dragIcon.gif",
                c = a.dragIconWidth, f = a.dragIconHeight);
            g = b.image(h, 0, 0, c, f);
            var h = b.image(h, 0, 0, c, f), k = 10, l = 20;
            d.panEventsEnabled && (k = 25, l = a.scrollbarHeight);
            var m = AmCharts.rect(b, k, l, "#000", .005), n = AmCharts.rect(b, k, l, "#000", .005);
            n.translate(-(k - c) / 2, -(l - f) / 2);
            m.translate(-(k - c) / 2, -(l - f) / 2);
            c = b.set([g, n]);
            b = b.set([h, m]);
            a.iconLeft = c;
            a.iconRight = b;
            c.mousedown(function () {
                a.leftDragStart()
            }).mouseup(function () {
                a.leftDragStop()
            }).mouseover(function () {
                a.iconRollOver()
            }).mouseout(function () {
                a.iconRollOut()
            }).touchstart(function (b) {
                a.leftDragStart()
            }).touchend(function () {
                a.leftDragStop()
            });
            b.mousedown(function () {
                a.rightDragStart()
            }).mouseup(function () {
                a.rightDragStop()
            }).mouseover(function () {
                a.iconRollOver()
            }).mouseout(function () {
                a.iconRollOut()
            }).touchstart(function (b) {
                a.rightDragStart()
            }).touchend(function () {
                a.rightDragStop()
            });
            AmCharts.ifArray(d.chartData) ? e.show() : e.hide();
            a.hideDragIcons();
            a.clipDragger(!1)
        }
        e.translate(a.x, a.y)
    }, updateScrollbarSize: function (a, b) {
        var c = this.dragger, d, e, f, g;
        this.rotate ? (d = 0, e = a, f = this.width + 1, g = b - a, c.setAttr("height", b - a), c.setAttr("y", e)) : (d = a,
            e = 0, f = b - a, g = this.height + 1, c.setAttr("width", b - a), c.setAttr("x", d));
        this.clipAndUpdate(d, e, f, g)
    }, updateScrollbar: function () {
        var a, b = !1, c, d, e = this.x, f = this.y, g = this.dragger, h = this.getDBox();
        c = h.x + e;
        d = h.y + f;
        var k = h.width, h = h.height, l = this.rotate, m = this.chart, n = this.width, p = this.height, q = m.mouseX, r = m.mouseY;
        a = this.initialMouse;
        this.forceClip && this.clipDragger(!0);
        m.mouseIsOver && (this.dragging && (m = this.initialCoord, l ? (a = m + (r - a), 0 > a && (a = 0), m = p - h, a > m && (a = m), g.setAttr("y", a)) : (a = m + (q - a), 0 > a && (a = 0), m = n - k,
        a > m && (a = m), g.setAttr("x", a)), this.clipDragger(!0)), this.resizingRight && (l ? (a = r - d, a + d > p + f && (a = p - d + f), 0 > a ? (this.resizingRight = !1, b = this.resizingLeft = !0) : (0 === a && (a = .1), g.setAttr("height", a))) : (a = q - c, a + c > n + e && (a = n - c + e), 0 > a ? (this.resizingRight = !1, b = this.resizingLeft = !0) : (0 === a && (a = .1), g.setAttr("width", a))), this.clipDragger(!0)), this.resizingLeft && (l ? (c = d, d = r, d < f && (d = f), d > p + f && (d = p + f), a = !0 === b ? c - d : h + c - d, 0 > a ? (this.resizingRight = !0, this.resizingLeft = !1, g.setAttr("y", c + h - f)) : (0 === a && (a = .1), g.setAttr("y",
            d - f), g.setAttr("height", a))) : (d = q, d < e && (d = e), d > n + e && (d = n + e), a = !0 === b ? c - d : k + c - d, 0 > a ? (this.resizingRight = !0, this.resizingLeft = !1, g.setAttr("x", c + k - e)) : (0 === a && (a = .1), g.setAttr("x", d - e), g.setAttr("width", a))), this.clipDragger(!0)))
    }, stopForceClip: function () {
        this.forceClip = !1
    }, clipDragger: function (a) {
        var b = this.getDBox();
        if (b) {
            var c = b.x, d = b.y, e = b.width, b = b.height, f = !1;
            if (this.rotate) {
                if (c = 0, e = this.width + 1, this.clipY != d || this.clipH != b)f = !0
            } else if (d = 0, b = this.height + 1, this.clipX != c || this.clipW != e)f = !0;
            f && (this.clipAndUpdate(c, d, e, b), a && (this.updateOnReleaseOnly || this.dispatchScrollbarEvent()))
        }
    }, maskGraphs: function () {
    }, clipAndUpdate: function (a, b, c, d) {
        this.clipX = a;
        this.clipY = b;
        this.clipW = c;
        this.clipH = d;
        this.selectedBG.clipRect(a, b, c, d);
        this.updateDragIconPositions();
        this.maskGraphs(a, b, c, d)
    }, dispatchScrollbarEvent: function () {
        if (this.skipEvent)this.skipEvent = !1; else {
            var a = this.chart;
            a.hideBalloon();
            var b = this.getDBox(), c = b.x, d = b.y, e = b.width, b = b.height;
            this.rotate ? (c = d, e = this.height / b) : e = this.width /
            e;
            a = {type: "zoomed", position: c, chart: a, target: this, multiplier: e};
            this.fire(a.type, a)
        }
    }, updateDragIconPositions: function () {
        var a = this.getDBox(), b = a.x, c = a.y, d = this.iconLeft, e = this.iconRight, f, g, h = this.scrollbarHeight;
        this.rotate ? (f = this.dragIconWidth, g = this.dragIconHeight, d.translate(this.x + (h - g) / 2, this.y + c - f / 2), e.translate(this.x + (h - g) / 2, this.y + c + a.height - f / 2)) : (f = this.dragIconHeight, g = this.dragIconWidth, d.translate(this.x + b - g / 2, this.y + (h - f) / 2), e.translate(this.x + b - g / 2 + a.width, this.y + (h - f) / 2))
    }, showDragIcons: function () {
        this.resizeEnabled &&
        (this.iconLeft.show(), this.iconRight.show())
    }, hideDragIcons: function () {
        if (!this.resizingLeft && !this.resizingRight && !this.dragging) {
            if (this.hideResizeGrips || !this.resizeEnabled)this.iconLeft.hide(), this.iconRight.hide();
            this.removeCursors()
        }
    }, removeCursors: function () {
        this.chart.setMouseCursor("auto")
    }, relativeZoom: function (a, b) {
        this.dragger.stop();
        this.multiplier = a;
        this.position = b;
        this.updateScrollbarSize(b, this.rotate ? b + this.height / a : b + this.width / a)
    }, destroy: function () {
        this.clear();
        AmCharts.remove(this.set);
        AmCharts.remove(this.iconRight);
        AmCharts.remove(this.iconLeft)
    }, clear: function () {
        clearInterval(this.interval)
    }, handleDragStart: function () {
        var a = this.chart;
        this.dragger.stop();
        this.removeCursors();
        this.dragging = !0;
        var b = this.getDBox();
        this.rotate ? (this.initialCoord = b.y, this.initialMouse = a.mouseY) : (this.initialCoord = b.x, this.initialMouse = a.mouseX)
    }, handleDragStop: function () {
        this.updateOnReleaseOnly && (this.updateScrollbar(), this.skipEvent = !1, this.dispatchScrollbarEvent());
        this.dragging = !1;
        this.mouseIsOver &&
        this.removeCursors();
        this.updateScrollbar()
    }, handleDraggerOver: function () {
        this.handleMouseOver()
    }, leftDragStart: function () {
        this.dragger.stop();
        this.resizingLeft = !0
    }, leftDragStop: function () {
        this.resizingLeft = !1;
        this.mouseIsOver || this.removeCursors();
        this.updateOnRelease()
    }, rightDragStart: function () {
        this.dragger.stop();
        this.resizingRight = !0
    }, rightDragStop: function () {
        this.resizingRight = !1;
        this.mouseIsOver || this.removeCursors();
        this.updateOnRelease()
    }, iconRollOut: function () {
        this.removeCursors()
    }, iconRollOver: function () {
        this.rotate ?
            this.chart.setMouseCursor("n-resize") : this.chart.setMouseCursor("e-resize");
        this.handleMouseOver()
    }, getDBox: function () {
        if (this.dragger)return this.dragger.getBBox()
    }, handleBgClick: function () {
        var a = this;
        if (!a.resizingRight && !a.resizingLeft) {
            a.zooming = !0;
            var b, c, d = a.scrollDuration, e = a.dragger;
            b = a.getDBox();
            var f = b.height, g = b.width;
            c = a.chart;
            var h = a.y, k = a.x, l = a.rotate;
            l ? (b = "y", c = c.mouseY - f / 2 - h, c = AmCharts.fitToBounds(c, 0, a.height - f)) : (b = "x", c = c.mouseX - g / 2 - k, c = AmCharts.fitToBounds(c, 0, a.width - g));
            a.updateOnReleaseOnly ?
                (a.skipEvent = !1, e.setAttr(b, c), a.dispatchScrollbarEvent(), a.clipDragger()) : (c = Math.round(c), l ? e.animate({y: c}, d, ">") : e.animate({x: c}, d, ">"), a.forceClip = !0, clearTimeout(a.forceTO), a.forceTO = setTimeout(function () {
                a.stopForceClip.call(a)
            }, 3E3 * d))
        }
    }, updateOnRelease: function () {
        this.updateOnReleaseOnly && (this.updateScrollbar(), this.skipEvent = !1, this.dispatchScrollbarEvent())
    }, handleReleaseOutside: function () {
        if (this.set) {
            if (this.resizingLeft || this.resizingRight || this.dragging)this.updateOnRelease(), this.removeCursors();
            this.mouseIsOver = this.dragging = this.resizingRight = this.resizingLeft = !1;
            this.hideDragIcons();
            this.updateScrollbar()
        }
    }, handleMouseOver: function () {
        this.mouseIsOver = !0;
        this.showDragIcons()
    }, handleMouseOut: function () {
        this.mouseIsOver = !1;
        this.hideDragIcons()
    }
});
AmCharts.ChartScrollbar = AmCharts.Class({
    inherits: AmCharts.SimpleChartScrollbar, construct: function (a) {
        this.cname = "ChartScrollbar";
        AmCharts.ChartScrollbar.base.construct.call(this, a);
        this.graphLineColor = "#BBBBBB";
        this.graphLineAlpha = 0;
        this.graphFillColor = "#BBBBBB";
        this.graphFillAlpha = 1;
        this.selectedGraphLineColor = "#888888";
        this.selectedGraphLineAlpha = 0;
        this.selectedGraphFillColor = "#888888";
        this.selectedGraphFillAlpha = 1;
        this.gridCount = 0;
        this.gridColor = "#FFFFFF";
        this.gridAlpha = .7;
        this.skipEvent = this.autoGridCount = !1;
        this.color = "#FFFFFF";
        this.scrollbarCreated = !1;
        this.offset = 0;
        AmCharts.applyTheme(this, a, this.cname)
    }, init: function () {
        var a = this.categoryAxis, b = this.chart;
        a || (this.categoryAxis = a = new AmCharts.CategoryAxis);
        a.chart = b;
        a.id = "scrollbar";
        a.dateFormats = b.categoryAxis.dateFormats;
        a.markPeriodChange = b.categoryAxis.markPeriodChange;
        a.boldPeriodBeginning = b.categoryAxis.boldPeriodBeginning;
        a.axisItemRenderer = AmCharts.RecItem;
        a.axisRenderer = AmCharts.RecAxis;
        a.guideFillRenderer = AmCharts.RecFill;
        a.inside = !0;
        a.fontSize =
            this.fontSize;
        a.tickLength = 0;
        a.axisAlpha = 0;
        AmCharts.isString(this.graph) && (this.graph = AmCharts.getObjById(b.graphs, this.graph));
        if (a = this.graph) {
            var c = this.valueAxis;
            c || (this.valueAxis = c = new AmCharts.ValueAxis, c.visible = !1, c.scrollbar = !0, c.axisItemRenderer = AmCharts.RecItem, c.axisRenderer = AmCharts.RecAxis, c.guideFillRenderer = AmCharts.RecFill, c.labelsEnabled = !1, c.chart = b);
            b = this.unselectedGraph;
            b || (b = new AmCharts.AmGraph, b.scrollbar = !0, this.unselectedGraph = b, b.negativeBase = a.negativeBase, b.noStepRisers =
                a.noStepRisers);
            b = this.selectedGraph;
            b || (b = new AmCharts.AmGraph, b.scrollbar = !0, this.selectedGraph = b, b.negativeBase = a.negativeBase, b.noStepRisers = a.noStepRisers)
        }
        this.scrollbarCreated = !0
    }, draw: function () {
        var a = this;
        AmCharts.ChartScrollbar.base.draw.call(a);
        a.scrollbarCreated || a.init();
        var b = a.chart, c = b.chartData, d = a.categoryAxis, e = a.rotate, f = a.x, g = a.y, h = a.width, k = a.height, l = b.categoryAxis, m = a.set;
        d.setOrientation(!e);
        d.parseDates = l.parseDates;
        d.rotate = e;
        d.equalSpacing = l.equalSpacing;
        d.minPeriod = l.minPeriod;
        d.startOnAxis = l.startOnAxis;
        d.viW = h;
        d.viH = k;
        d.width = h;
        d.height = k;
        d.gridCount = a.gridCount;
        d.gridColor = a.gridColor;
        d.gridAlpha = a.gridAlpha;
        d.color = a.color;
        d.tickLength = 0;
        d.axisAlpha = 0;
        d.autoGridCount = a.autoGridCount;
        d.parseDates && !d.equalSpacing && d.timeZoom(b.firstTime, b.lastTime);
        d.zoom(0, c.length - 1);
        if (l = a.graph) {
            var n = a.valueAxis, p = l.valueAxis;
            n.id = p.id;
            n.rotate = e;
            n.setOrientation(e);
            n.width = h;
            n.height = k;
            n.viW = h;
            n.viH = k;
            n.dataProvider = c;
            n.reversed = p.reversed;
            n.logarithmic = p.logarithmic;
            n.gridAlpha =
                0;
            n.axisAlpha = 0;
            m.push(n.set);
            e ? (n.y = g, n.x = 0) : (n.x = f, n.y = 0);
            var f = Infinity, g = -Infinity, q;
            for (q = 0; q < c.length; q++) {
                var r = c[q].axes[p.id].graphs[l.id].values, s;
                for (s in r)if (r.hasOwnProperty(s) && "percents" != s && "total" != s) {
                    var w = r[s];
                    w < f && (f = w);
                    w > g && (g = w)
                }
            }
            Infinity != f && (n.minimum = f);
            -Infinity != g && (n.maximum = g + .1 * (g - f));
            f == g && (n.minimum -= 1, n.maximum += 1);
            void 0 !== a.minimum && (n.minimum = a.minimum);
            void 0 !== a.maximum && (n.maximum = a.maximum);
            n.zoom(0, c.length - 1);
            s = a.unselectedGraph;
            s.id = l.id;
            s.rotate = e;
            s.chart =
                b;
            s.data = c;
            s.valueAxis = n;
            s.chart = l.chart;
            s.categoryAxis = a.categoryAxis;
            s.periodSpan = l.periodSpan;
            s.valueField = l.valueField;
            s.openField = l.openField;
            s.closeField = l.closeField;
            s.highField = l.highField;
            s.lowField = l.lowField;
            s.lineAlpha = a.graphLineAlpha;
            s.lineColorR = a.graphLineColor;
            s.fillAlphas = a.graphFillAlpha;
            s.fillColorsR = a.graphFillColor;
            s.connect = l.connect;
            s.hidden = l.hidden;
            s.width = h;
            s.height = k;
            s.pointPosition = l.pointPosition;
            s.stepDirection = l.stepDirection;
            s.periodSpan = l.periodSpan;
            p = a.selectedGraph;
            p.id = l.id;
            p.rotate = e;
            p.chart = b;
            p.data = c;
            p.valueAxis = n;
            p.chart = l.chart;
            p.categoryAxis = d;
            p.periodSpan = l.periodSpan;
            p.valueField = l.valueField;
            p.openField = l.openField;
            p.closeField = l.closeField;
            p.highField = l.highField;
            p.lowField = l.lowField;
            p.lineAlpha = a.selectedGraphLineAlpha;
            p.lineColorR = a.selectedGraphLineColor;
            p.fillAlphas = a.selectedGraphFillAlpha;
            p.fillColorsR = a.selectedGraphFillColor;
            p.connect = l.connect;
            p.hidden = l.hidden;
            p.width = h;
            p.height = k;
            p.pointPosition = l.pointPosition;
            p.stepDirection = l.stepDirection;
            p.periodSpan = l.periodSpan;
            b = a.graphType;
            b || (b = l.type);
            s.type = b;
            p.type = b;
            c = c.length - 1;
            s.zoom(0, c);
            p.zoom(0, c);
            p.set.click(function () {
                a.handleBackgroundClick()
            }).mouseover(function () {
                a.handleMouseOver()
            }).mouseout(function () {
                a.handleMouseOut()
            });
            s.set.click(function () {
                a.handleBackgroundClick()
            }).mouseover(function () {
                a.handleMouseOver()
            }).mouseout(function () {
                a.handleMouseOut()
            });
            m.push(s.set);
            m.push(p.set)
        }
        m.push(d.set);
        m.push(d.labelsSet);
        a.bg.toBack();
        a.invisibleBg.toFront();
        a.dragger.toFront();
        a.iconLeft.toFront();
        a.iconRight.toFront()
    }, timeZoom: function (a, b, c) {
        this.startTime = a;
        this.endTime = b;
        this.timeDifference = b - a;
        this.skipEvent = !AmCharts.toBoolean(c);
        this.zoomScrollbar();
        this.skipEvent || this.dispatchScrollbarEvent()
    }, zoom: function (a, b) {
        this.start = a;
        this.end = b;
        this.skipEvent = !0;
        this.zoomScrollbar()
    }, dispatchScrollbarEvent: function () {
        if (this.skipEvent)this.skipEvent = !1; else {
            var a = this.chart.chartData, b, c, d = this.dragger.getBBox();
            b = d.x;
            var e = d.y, f = d.width, d = d.height, g = this.chart;
            this.rotate ? (b = e, c = d) : c = f;
            f =
            {type: "zoomed", target: this};
            f.chart = g;
            var h = this.categoryAxis, k = this.stepWidth, e = g.minSelectedTime, d = !1;
            if (h.parseDates && !h.equalSpacing) {
                if (a = g.lastTime, g = g.firstTime, h.minDuration(), h = Math.round(b / k) + g, b = this.dragging ? h + this.timeDifference : Math.round((b + c) / k) + g, h > b && (h = b), 0 < e && b - h < e && (b = Math.round(h + (b - h) / 2), d = Math.round(e / 2), h = b - d, b += d, d = !0), b > a && (b = a), b - e < h && (h = b - e), h < g && (h = g), h + e > b && (b = h + e), h != this.startTime || b != this.endTime)this.startTime = h, this.endTime = b, f.start = h, f.end = b, f.startDate = new Date(h),
                    f.endDate = new Date(b), this.fire(f.type, f)
            } else if (h.startOnAxis || (b += k / 2), c -= this.stepWidth / 2, e = h.xToIndex(b), b = h.xToIndex(b + c), e != this.start || this.end != b)h.startOnAxis && (this.resizingRight && e == b && b++, this.resizingLeft && e == b && (0 < e ? e-- : b = 1)), this.start = e, this.end = this.dragging ? this.start + this.difference : b, f.start = this.start, f.end = this.end, h.parseDates && (a[this.start] && (f.startDate = new Date(a[this.start].time)), a[this.end] && (f.endDate = new Date(a[this.end].time))), this.fire(f.type, f);
            d && this.zoomScrollbar()
        }
    },
    zoomScrollbar: function () {
        var a, b;
        a = this.chart;
        var c = a.chartData, d = this.categoryAxis;
        d.parseDates && !d.equalSpacing ? (c = d.stepWidth, d = a.firstTime, a = c * (this.startTime - d), b = c * (this.endTime - d)) : (a = c[this.start].x[d.id], b = c[this.end].x[d.id], c = d.stepWidth, d.startOnAxis || (d = c / 2, a -= d, b += d));
        this.stepWidth = c;
        this.updateScrollbarSize(a, b)
    }, maskGraphs: function (a, b, c, d) {
        var e = this.selectedGraph;
        e && e.set.clipRect(a, b, c, d)
    }, handleDragStart: function () {
        AmCharts.ChartScrollbar.base.handleDragStart.call(this);
        this.difference =
            this.end - this.start;
        this.timeDifference = this.endTime - this.startTime;
        0 > this.timeDifference && (this.timeDifference = 0)
    }, handleBackgroundClick: function () {
        AmCharts.ChartScrollbar.base.handleBackgroundClick.call(this);
        this.dragging || (this.difference = this.end - this.start, this.timeDifference = this.endTime - this.startTime, 0 > this.timeDifference && (this.timeDifference = 0))
    }
});
AmCharts.AmBalloon = AmCharts.Class({
    construct: function (a) {
        this.cname = "AmBalloon";
        this.enabled = !0;
        this.fillColor = "#FFFFFF";
        this.fillAlpha = .8;
        this.borderThickness = 2;
        this.borderColor = "#FFFFFF";
        this.borderAlpha = 1;
        this.cornerRadius = 0;
        this.maximumWidth = 220;
        this.horizontalPadding = 8;
        this.verticalPadding = 4;
        this.pointerWidth = 6;
        this.pointerOrientation = "V";
        this.color = "#000000";
        this.adjustBorderColor = !0;
        this.show = this.follow = this.showBullet = !1;
        this.bulletSize = 3;
        this.shadowAlpha = .4;
        this.shadowColor = "#000000";
        this.fadeOutDuration =
            this.animationDuration = .3;
        this.fixedPosition = !1;
        this.offsetY = 6;
        this.offsetX = 1;
        this.textAlign = "center";
        AmCharts.isModern || (this.offsetY *= 1.5);
        AmCharts.applyTheme(this, a, this.cname)
    }, draw: function () {
        var a = this.pointToX, b = this.pointToY;
        this.deltaSignX = this.deltaSignY = 1;
        var c = this.chart;
        AmCharts.VML && (this.fadeOutDuration = 0);
        this.xAnim && c.stopAnim(this.xAnim);
        this.yAnim && c.stopAnim(this.yAnim);
        if (!isNaN(a)) {
            var d = this.follow, e = c.container, f = this.set;
            AmCharts.remove(f);
            this.removeDiv();
            f = e.set();
            f.node.style.pointerEvents =
                "none";
            this.set = f;
            c.balloonsSet.push(f);
            if (this.show) {
                var g = this.l, h = this.t, k = this.r, l = this.b, m = this.balloonColor, n = this.fillColor, p = this.borderColor, q = n;
                void 0 != m && (this.adjustBorderColor ? q = p = m : n = m);
                var r = this.horizontalPadding, s = this.verticalPadding, w = this.pointerWidth, v = this.pointerOrientation, t = this.cornerRadius, u = c.fontFamily, x = this.fontSize;
                void 0 == x && (x = c.fontSize);
                var m = document.createElement("div"), E = m.style;
                E.pointerEvents = "none";
                E.position = "absolute";
                var A = this.minWidth, z = "";
                isNaN(A) ||
                (z = "min-width:" + (A - 2 * r) + "px; ");
                m.innerHTML = '<div style="text-align:' + this.textAlign + "; " + z + "max-width:" + this.maxWidth + "px; font-size:" + x + "px; color:" + this.color + "; font-family:" + u + '">' + this.text + "</div>";
                c.chartDiv.appendChild(m);
                this.textDiv = m;
                x = m.offsetWidth;
                u = m.offsetHeight;
                m.clientHeight && (x = m.clientWidth, u = m.clientHeight);
                u += 2 * s;
                z = x + 2 * r;
                !isNaN(A) && z < A && (z = A);
                window.opera && (u += 2);
                var H = !1, x = this.offsetY;
                c.handDrawn && (x += c.handDrawScatter + 2);
                "H" != v ? (A = a - z / 2, b < h + u + 10 && "down" != v ? (H = !0, d && (b += x),
                    x = b + w, this.deltaSignY = -1) : (d && (b -= x), x = b - u - w, this.deltaSignY = 1)) : (2 * w > u && (w = u / 2), x = b - u / 2, a < g + (k - g) / 2 ? (A = a + w, this.deltaSignX = -1) : (A = a - z - w, this.deltaSignX = 1));
                x + u >= l && (x = l - u);
                x < h && (x = h);
                A < g && (A = g);
                A + z > k && (A = k - z);
                var h = x + s, l = A + r, s = this.shadowAlpha, F = this.shadowColor, r = this.borderThickness, G = this.bulletSize, V;
                0 < t || 0 === w ? (0 < s && (a = AmCharts.rect(e, z, u, n, 0, r + 1, F, s, this.cornerRadius), AmCharts.isModern ? a.translate(1, 1) : a.translate(4, 4), f.push(a)), n = AmCharts.rect(e, z, u, n, this.fillAlpha, r, p, this.borderAlpha,
                    this.cornerRadius), this.showBullet && (V = AmCharts.circle(e, G, q, this.fillAlpha), f.push(V))) : (q = [], t = [], "H" != v ? (g = a - A, g > z - w && (g = z - w), g < w && (g = w), q = [0, g - w, a - A, g + w, z, z, 0, 0], t = H ? [0, 0, b - x, 0, 0, u, u, 0] : [u, u, b - x, u, u, 0, 0, u]) : (q = b - x, q > u - w && (q = u - w), q < w && (q = w), t = [0, q - w, b - x, q + w, u, u, 0, 0], q = a < g + (k - g) / 2 ? [0, 0, A < a ? 0 : a - A, 0, 0, z, z, 0] : [z, z, A + z > a ? z : a - A, z, z, 0, 0, z]), 0 < s && (a = AmCharts.polygon(e, q, t, n, 0, r, F, s), a.translate(1, 1), f.push(a)), n = AmCharts.polygon(e, q, t, n, this.fillAlpha, r, p, this.borderAlpha));
                this.bg = n;
                f.push(n);
                n.toFront();
                e = 1 * this.deltaSignX;
                E.left = l + "px";
                E.top = h + "px";
                f.translate(A - e, x);
                n = n.getBBox();
                this.bottom = x + u + 1;
                this.yPos = n.y + x;
                V && V.translate(this.pointToX - A + e, b - x);
                b = this.animationDuration;
                0 < this.animationDuration && !d && !isNaN(this.prevX) && (f.translate(this.prevX, this.prevY), f.animate({translate: A - e + "," + x}, b, "easeOutSine"), m && (E.left = this.prevTX + "px", E.top = this.prevTY + "px", this.xAnim = c.animate({node: m}, "left", this.prevTX, l, b, "easeOutSine", "px"), this.yAnim = c.animate({node: m}, "top", this.prevTY, h, b, "easeOutSine",
                    "px")));
                this.prevX = A - e;
                this.prevY = x;
                this.prevTX = l;
                this.prevTY = h
            }
        }
    }, followMouse: function () {
        if (this.follow && this.show) {
            var a = this.chart.mouseX - this.offsetX * this.deltaSignX, b = this.chart.mouseY;
            this.pointToX = a;
            this.pointToY = b;
            if (a != this.previousX || b != this.previousY)if (this.previousX = a, this.previousY = b, 0 === this.cornerRadius)this.draw(); else {
                var c = this.set;
                if (c) {
                    var d = c.getBBox(), a = a - d.width / 2, e = b - d.height - 10;
                    a < this.l && (a = this.l);
                    a > this.r - d.width && (a = this.r - d.width);
                    e < this.t && (e = b + 10);
                    c.translate(a, e);
                    b = this.textDiv.style;
                    b.left = a + this.horizontalPadding + "px";
                    b.top = e + this.verticalPadding + "px"
                }
            }
        }
    }, changeColor: function (a) {
        this.balloonColor = a
    }, setBounds: function (a, b, c, d) {
        this.l = a;
        this.t = b;
        this.r = c;
        this.b = d;
        this.destroyTO && clearTimeout(this.destroyTO)
    }, showBalloon: function (a) {
        this.text = a;
        this.show = !0;
        this.destroyTO && clearTimeout(this.destroyTO);
        a = this.chart;
        this.fadeAnim1 && a.stopAnim(this.fadeAnim1);
        this.fadeAnim2 && a.stopAnim(this.fadeAnim2);
        this.draw()
    }, hide: function () {
        var a = this, b = a.fadeOutDuration,
            c = a.chart;
        if (0 < b) {
            a.destroyTO = setTimeout(function () {
                a.destroy.call(a)
            }, 1E3 * b);
            a.follow = !1;
            a.show = !1;
            var d = a.set;
            d && (d.setAttr("opacity", a.fillAlpha), a.fadeAnim1 = d.animate({opacity: 0}, b, "easeInSine"));
            a.textDiv && (a.fadeAnim2 = c.animate({node: a.textDiv}, "opacity", 1, 0, b, "easeInSine", ""))
        } else a.show = !1, a.follow = !1, a.destroy()
    }, setPosition: function (a, b, c) {
        this.pointToX = a;
        this.pointToY = b;
        c && (a == this.previousX && b == this.previousY || this.draw());
        this.previousX = a;
        this.previousY = b
    }, followCursor: function (a) {
        var b =
            this;
        (b.follow = a) ? (b.pShowBullet = b.showBullet, b.showBullet = !1) : void 0 !== b.pShowBullet && (b.showBullet = b.pShowBullet);
        clearInterval(b.interval);
        var c = b.chart.mouseX, d = b.chart.mouseY;
        !isNaN(c) && a && (b.pointToX = c - b.offsetX * b.deltaSignX, b.pointToY = d, b.followMouse(), b.interval = setInterval(function () {
            b.followMouse.call(b)
        }, 40))
    }, removeDiv: function () {
        if (this.textDiv) {
            var a = this.textDiv.parentNode;
            a && a.removeChild(this.textDiv)
        }
    }, destroy: function () {
        clearInterval(this.interval);
        AmCharts.remove(this.set);
        this.removeDiv();
        this.set = null
    }
});
AmCharts.AmCoordinateChart = AmCharts.Class({
    inherits: AmCharts.AmChart, construct: function (a) {
        AmCharts.AmCoordinateChart.base.construct.call(this, a);
        this.theme = a;
        this.createEvents("rollOverGraphItem", "rollOutGraphItem", "clickGraphItem", "doubleClickGraphItem", "rightClickGraphItem", "clickGraph", "rollOverGraph", "rollOutGraph");
        this.startAlpha = 1;
        this.startDuration = 0;
        this.startEffect = "elastic";
        this.sequencedAnimation = !0;
        this.colors = "#FF6600 #FCD202 #B0DE09 #0D8ECF #2A0CD0 #CD0D74 #CC0000 #00CC00 #0000CC #DDDDDD #999999 #333333 #990000".split(" ");
        this.balloonDateFormat = "MMM DD, YYYY";
        this.valueAxes = [];
        this.graphs = [];
        this.guides = [];
        this.gridAboveGraphs = !1;
        AmCharts.applyTheme(this, a, "AmCoordinateChart")
    }, initChart: function () {
        AmCharts.AmCoordinateChart.base.initChart.call(this);
        var a = this.categoryAxis;
        a && (this.categoryAxis = AmCharts.processObject(a, AmCharts.CategoryAxis, this.theme));
        this.processValueAxes();
        this.createValueAxes();
        this.processGraphs();
        this.processGuides();
        AmCharts.VML && (this.startAlpha = 1);
        this.setLegendData(this.graphs);
        this.gridAboveGraphs &&
        this.gridSet.toFront()
    }, createValueAxes: function () {
        if (0 === this.valueAxes.length) {
            var a = new AmCharts.ValueAxis;
            this.addValueAxis(a)
        }
    }, parseData: function () {
        this.processValueAxes();
        this.processGraphs()
    }, parseSerialData: function () {
        var a = this.graphs, b, c = {}, d = this.seriesIdField;
        d || (d = this.categoryField);
        this.chartData = [];
        var e = this.dataProvider;
        if (e) {
            var f = !1, g, h = this.categoryAxis, k, l;
            h && (f = h.parseDates, k = h.forceShowField, l = h.labelColorField, g = h.categoryFunction);
            var m, n, p = {}, q;
            f && (b = AmCharts.extractPeriod(h.minPeriod),
                m = b.period, n = b.count, q = AmCharts.getPeriodDuration(m, n));
            var r = {};
            this.lookupTable = r;
            var s, w = this.dataDateFormat, v = {};
            for (s = 0; s < e.length; s++) {
                var t = {}, u = e[s];
                b = u[this.categoryField];
                t.dataContext = u;
                t.category = g ? g(b, u, h) : String(b);
                k && (t.forceShow = u[k]);
                l && (t.labelColor = u[l]);
                r[u[d]] = t;
                if (f && (b = h.categoryFunction ? h.categoryFunction(b, u, h) : b instanceof Date ? AmCharts.newDate(b, h.minPeriod) : w ? AmCharts.stringToDate(b, w) : new Date(b), b = AmCharts.resetDateToMin(b, m, n, h.firstDayOfWeek), t.category = b, t.time = b.getTime(),
                        isNaN(t.time)))continue;
                var x = this.valueAxes;
                t.axes = {};
                t.x = {};
                var E;
                for (E = 0; E < x.length; E++) {
                    var A = x[E].id;
                    t.axes[A] = {};
                    t.axes[A].graphs = {};
                    var z;
                    for (z = 0; z < a.length; z++) {
                        b = a[z];
                        var H = b.id, F = b.periodValue;
                        if (b.valueAxis.id == A) {
                            t.axes[A].graphs[H] = {};
                            var G = {};
                            G.index = s;
                            var V = u;
                            b.dataProvider && (V = c);
                            G.values = this.processValues(V, b, F);
                            !b.connect && v && v[H] && t.time - p[H] > 1.1 * q && (v[H].gap = !0);
                            this.processFields(b, G, V);
                            G.category = t.category;
                            G.serialDataItem = t;
                            G.graph = b;
                            t.axes[A].graphs[H] = G;
                            p[H] = t.time;
                            v[H] =
                                G
                        }
                    }
                }
                this.chartData[s] = t
            }
        }
        for (c = 0; c < a.length; c++)b = a[c], b.dataProvider && this.parseGraphData(b)
    }, processValues: function (a, b, c) {
        var d = {}, e, f = !1;
        "candlestick" != b.type && "ohlc" != b.type || "" === c || (f = !0);
        e = Number(a[b.valueField + c]);
        isNaN(e) || (d.value = e);
        e = Number(a[b.errorField + c]);
        isNaN(e) || (d.error = e);
        f && (c = "Open");
        e = Number(a[b.openField + c]);
        isNaN(e) || (d.open = e);
        f && (c = "Close");
        e = Number(a[b.closeField + c]);
        isNaN(e) || (d.close = e);
        f && (c = "Low");
        e = Number(a[b.lowField + c]);
        isNaN(e) || (d.low = e);
        f && (c = "High");
        e = Number(a[b.highField +
        c]);
        isNaN(e) || (d.high = e);
        return d
    }, parseGraphData: function (a) {
        var b = a.dataProvider, c = a.seriesIdField;
        c || (c = this.seriesIdField);
        c || (c = this.categoryField);
        var d;
        for (d = 0; d < b.length; d++) {
            var e = b[d], f = this.lookupTable[String(e[c])], g = a.valueAxis.id;
            f && (g = f.axes[g].graphs[a.id], g.serialDataItem = f, g.values = this.processValues(e, a, a.periodValue), this.processFields(a, g, e))
        }
    }, addValueAxis: function (a) {
        a.chart = this;
        this.valueAxes.push(a);
        this.validateData()
    }, removeValueAxesAndGraphs: function () {
        var a = this.valueAxes,
            b;
        for (b = a.length - 1; -1 < b; b--)this.removeValueAxis(a[b])
    }, removeValueAxis: function (a) {
        var b = this.graphs, c;
        for (c = b.length - 1; 0 <= c; c--) {
            var d = b[c];
            d && d.valueAxis == a && this.removeGraph(d)
        }
        b = this.valueAxes;
        for (c = b.length - 1; 0 <= c; c--)b[c] == a && b.splice(c, 1);
        this.validateData()
    }, addGraph: function (a) {
        this.graphs.push(a);
        this.chooseGraphColor(a, this.graphs.length - 1);
        this.validateData()
    }, removeGraph: function (a) {
        var b = this.graphs, c;
        for (c = b.length - 1; 0 <= c; c--)b[c] == a && (b.splice(c, 1), a.destroy());
        this.validateData()
    },
    processValueAxes: function () {
        var a = this.valueAxes, b;
        for (b = 0; b < a.length; b++) {
            var c = a[b], c = AmCharts.processObject(c, AmCharts.ValueAxis, this.theme);
            a[b] = c;
            c.chart = this;
            c.id || (c.id = "valueAxisAuto" + b + "_" + (new Date).getTime());
            void 0 === c.usePrefixes && (c.usePrefixes = this.usePrefixes)
        }
    }, processGuides: function () {
        var a = this.guides, b = this.categoryAxis;
        if (a)for (var c = 0; c < a.length; c++) {
            var d = a[c];
            (void 0 !== d.category || void 0 !== d.date) && b && b.addGuide(d);
            var e = d.valueAxis;
            e ? (AmCharts.isString(e) && (e = this.getValueAxisById(e)),
                e ? e.addGuide(d) : this.valueAxes[0].addGuide(d)) : isNaN(d.value) || this.valueAxes[0].addGuide(d)
        }
    }, processGraphs: function () {
        var a = this.graphs, b;
        for (b = 0; b < a.length; b++) {
            var c = a[b], c = AmCharts.processObject(c, AmCharts.AmGraph, this.theme);
            a[b] = c;
            this.chooseGraphColor(c, b);
            c.chart = this;
            AmCharts.isString(c.valueAxis) && (c.valueAxis = this.getValueAxisById(c.valueAxis));
            c.valueAxis || (c.valueAxis = this.valueAxes[0]);
            c.id || (c.id = "graphAuto" + b + "_" + (new Date).getTime())
        }
    }, formatString: function (a, b, c) {
        var d = b.graph,
            e = d.valueAxis;
        e.duration && b.values.value && (e = AmCharts.formatDuration(b.values.value, e.duration, "", e.durationUnits, e.maxInterval, e.numberFormatter), a = a.split("[[value]]").join(e));
        a = AmCharts.massReplace(a, {"[[title]]": d.title, "[[description]]": b.description});
        a = c ? AmCharts.fixNewLines(a) : AmCharts.fixBrakes(a);
        return a = AmCharts.cleanFromEmpty(a)
    }, getBalloonColor: function (a, b, c) {
        var d = a.lineColor, e = a.balloonColor;
        c && (e = d);
        c = a.fillColorsR;
        "object" == typeof c ? d = c[0] : void 0 !== c && (d = c);
        b.isNegative && (c = a.negativeLineColor,
            a = a.negativeFillColors, "object" == typeof a ? c = a[0] : void 0 !== a && (c = a), void 0 !== c && (d = c));
        void 0 !== b.color && (d = b.color);
        void 0 === e && (e = d);
        return e
    }, getGraphById: function (a) {
        return AmCharts.getObjById(this.graphs, a)
    }, getValueAxisById: function (a) {
        return AmCharts.getObjById(this.valueAxes, a)
    }, processFields: function (a, b, c) {
        if (a.itemColors) {
            var d = a.itemColors, e = b.index;
            b.color = e < d.length ? d[e] : AmCharts.randomColor()
        }
        d = "lineColor color alpha fillColors description bullet customBullet bulletSize bulletConfig url labelColor dashLength pattern".split(" ");
        for (e = 0; e < d.length; e++) {
            var f = d[e], g = a[f + "Field"];
            g && (g = c[g], AmCharts.isDefined(g) && (b[f] = g))
        }
        b.dataContext = c
    }, chooseGraphColor: function (a, b) {
        if (a.lineColor)a.lineColorR = a.lineColor; else {
            var c;
            c = this.colors.length > b ? this.colors[b] : AmCharts.randomColor();
            a.lineColorR = c
        }
        a.fillColorsR = a.fillColors ? a.fillColors : a.lineColorR;
        a.bulletBorderColorR = a.bulletBorderColor ? a.bulletBorderColor : a.useLineColorForBulletBorder ? a.lineColorR : a.bulletColor;
        a.bulletColorR = a.bulletColor ? a.bulletColor : a.lineColorR;
        if (c =
                this.patterns)a.pattern = c[b]
    }, handleLegendEvent: function (a) {
        var b = a.type;
        a = a.dataItem;
        if (!this.legend.data && a) {
            var c = a.hidden, d = a.showBalloon;
            switch (b) {
                case "clickMarker":
                    this.textClickEnabled && (d ? this.hideGraphsBalloon(a) : this.showGraphsBalloon(a));
                    break;
                case "clickLabel":
                    d ? this.hideGraphsBalloon(a) : this.showGraphsBalloon(a);
                    break;
                case "rollOverItem":
                    c || this.highlightGraph(a);
                    break;
                case "rollOutItem":
                    c || this.unhighlightGraph();
                    break;
                case "hideItem":
                    this.hideGraph(a);
                    break;
                case "showItem":
                    this.showGraph(a)
            }
        }
    },
    highlightGraph: function (a) {
        var b = this.graphs, c, d = .2;
        this.legend && (d = this.legend.rollOverGraphAlpha);
        if (1 != d)for (c = 0; c < b.length; c++) {
            var e = b[c];
            e != a && e.changeOpacity(d)
        }
    }, unhighlightGraph: function () {
        var a;
        this.legend && (a = this.legend.rollOverGraphAlpha);
        if (1 != a) {
            a = this.graphs;
            var b;
            for (b = 0; b < a.length; b++)a[b].changeOpacity(1)
        }
    }, showGraph: function (a) {
        a.switchable && (a.hidden = !1, this.dataChanged = !0, "xy" != this.type && (this.marginsUpdated = !1), this.chartCreated && this.initChart())
    }, hideGraph: function (a) {
        a.switchable &&
        (this.dataChanged = !0, "xy" != this.type && (this.marginsUpdated = !1), a.hidden = !0, this.chartCreated && this.initChart())
    }, hideGraphsBalloon: function (a) {
        a.showBalloon = !1;
        this.updateLegend()
    }, showGraphsBalloon: function (a) {
        a.showBalloon = !0;
        this.updateLegend()
    }, updateLegend: function () {
        this.legend && this.legend.invalidateSize()
    }, resetAnimation: function () {
        var a = this.graphs;
        if (a) {
            var b;
            for (b = 0; b < a.length; b++)a[b].animationPlayed = !1
        }
    }, animateAgain: function () {
        this.resetAnimation();
        this.validateNow()
    }
});
AmCharts.AmSlicedChart = AmCharts.Class({
    inherits: AmCharts.AmChart, construct: function (a) {
        this.createEvents("rollOverSlice", "rollOutSlice", "clickSlice", "pullOutSlice", "pullInSlice", "rightClickSlice");
        AmCharts.AmSlicedChart.base.construct.call(this, a);
        this.colors = "#FF0F00 #FF6600 #FF9E01 #FCD202 #F8FF01 #B0DE09 #04D215 #0D8ECF #0D52D1 #2A0CD0 #8A0CCF #CD0D74 #754DEB #DDDDDD #999999 #333333 #000000 #57032A #CA9726 #990000 #4B0C25".split(" ");
        this.alpha = 1;
        this.groupPercent = 0;
        this.groupedTitle = "Other";
        this.groupedPulled = !1;
        this.groupedAlpha = 1;
        this.marginLeft = 0;
        this.marginBottom = this.marginTop = 10;
        this.marginRight = 0;
        this.hoverAlpha = 1;
        this.outlineColor = "#FFFFFF";
        this.outlineAlpha = 0;
        this.outlineThickness = 1;
        this.startAlpha = 0;
        this.startDuration = 1;
        this.startEffect = "bounce";
        this.sequencedAnimation = !0;
        this.pullOutDuration = 1;
        this.pullOutEffect = "bounce";
        this.pullOnHover = this.pullOutOnlyOne = !1;
        this.labelsEnabled = !0;
        this.labelTickColor = "#000000";
        this.labelTickAlpha = .2;
        this.hideLabelsPercent = 0;
        this.urlTarget = "_self";
        this.autoMarginOffset =
            10;
        this.gradientRatio = [];
        this.maxLabelWidth = 200;
        AmCharts.applyTheme(this, a, "AmSlicedChart")
    }, initChart: function () {
        AmCharts.AmSlicedChart.base.initChart.call(this);
        this.dataChanged && (this.parseData(), this.dispatchDataUpdated = !0, this.dataChanged = !1, this.setLegendData(this.chartData));
        this.drawChart()
    }, handleLegendEvent: function (a) {
        var b = a.type, c = a.dataItem, d = this.legend;
        if (!d.data && c) {
            var e = c.hidden;
            a = a.event;
            switch (b) {
                case "clickMarker":
                    e || d.switchable || this.clickSlice(c, a);
                    break;
                case "clickLabel":
                    e ||
                    this.clickSlice(c, a, !1);
                    break;
                case "rollOverItem":
                    e || this.rollOverSlice(c, !1, a);
                    break;
                case "rollOutItem":
                    e || this.rollOutSlice(c, a);
                    break;
                case "hideItem":
                    this.hideSlice(c, a);
                    break;
                case "showItem":
                    this.showSlice(c, a)
            }
        }
    }, invalidateVisibility: function () {
        this.recalculatePercents();
        this.initChart();
        var a = this.legend;
        a && a.invalidateSize()
    }, addEventListeners: function (a, b) {
        var c = this;
        a.mouseover(function (a) {
            c.rollOverSlice(b, !0, a)
        }).mouseout(function (a) {
            c.rollOutSlice(b, a)
        }).touchend(function (a) {
            c.rollOverSlice(b,
                a);
            c.panEventsEnabled && c.clickSlice(b, a)
        }).touchstart(function (a) {
            c.rollOverSlice(b, a)
        }).click(function (a) {
            c.clickSlice(b, a)
        }).contextmenu(function (a) {
            c.handleRightClick(b, a)
        })
    }, formatString: function (a, b, c) {
        a = AmCharts.formatValue(a, b, ["value"], this.nf, "", this.usePrefixes, this.prefixesOfSmallNumbers, this.prefixesOfBigNumbers);
        a = AmCharts.formatValue(a, b, ["percents"], this.pf);
        a = AmCharts.massReplace(a, {"[[title]]": b.title, "[[description]]": b.description});
        -1 != a.indexOf("[[") && (a = AmCharts.formatDataContextValue(a,
            b.dataContext));
        a = c ? AmCharts.fixNewLines(a) : AmCharts.fixBrakes(a);
        return a = AmCharts.cleanFromEmpty(a)
    }, startSlices: function () {
        var a;
        for (a = 0; a < this.chartData.length; a++)0 < this.startDuration && this.sequencedAnimation ? this.setStartTO(a) : this.startSlice(this.chartData[a])
    }, setStartTO: function (a) {
        var b = this;
        a = setTimeout(function () {
            b.startSequenced.call(b)
        }, b.startDuration / b.chartData.length * 500 * a);
        b.timeOuts.push(a)
    }, pullSlices: function (a) {
        var b = this.chartData, c;
        for (c = 0; c < b.length; c++) {
            var d = b[c];
            d.pulled &&
            this.pullSlice(d, 1, a)
        }
    }, startSequenced: function () {
        var a = this.chartData, b;
        for (b = 0; b < a.length; b++)if (!a[b].started) {
            this.startSlice(this.chartData[b]);
            break
        }
    }, startSlice: function (a) {
        a.started = !0;
        var b = a.wedge, c = this.startDuration;
        b && 0 < c && (0 < a.alpha && b.show(), b.translate(a.startX, a.startY), b.animate({
            opacity: 1,
            translate: "0,0"
        }, c, this.startEffect))
    }, showLabels: function () {
        var a = this.chartData, b;
        for (b = 0; b < a.length; b++) {
            var c = a[b];
            if (0 < c.alpha) {
                var d = c.label;
                d && d.show();
                (c = c.tick) && c.show()
            }
        }
    }, showSlice: function (a) {
        isNaN(a) ?
            a.hidden = !1 : this.chartData[a].hidden = !1;
        this.invalidateVisibility()
    }, hideSlice: function (a) {
        isNaN(a) ? a.hidden = !0 : this.chartData[a].hidden = !0;
        this.hideBalloon();
        this.invalidateVisibility()
    }, rollOverSlice: function (a, b, c) {
        isNaN(a) || (a = this.chartData[a]);
        clearTimeout(this.hoverInt);
        if (!a.hidden) {
            this.pullOnHover && this.pullSlice(a, 1);
            1 > this.hoverAlpha && a.wedge && a.wedge.attr({opacity: this.hoverAlpha});
            var d = a.balloonX, e = a.balloonY;
            a.pulled && (d += a.pullX, e += a.pullY);
            var f = this.formatString(this.balloonText,
                a, !0), g = this.balloonFunction;
            g && (f = g(a, f));
            g = AmCharts.adjustLuminosity(a.color, -.15);
            this.showBalloon(f, g, b, d, e);
            a = {type: "rollOverSlice", dataItem: a, chart: this, event: c};
            this.fire(a.type, a)
        }
    }, rollOutSlice: function (a, b) {
        isNaN(a) || (a = this.chartData[a]);
        a.wedge && a.wedge.attr({opacity: 1});
        this.hideBalloon();
        var c = {type: "rollOutSlice", dataItem: a, chart: this, event: b};
        this.fire(c.type, c)
    }, clickSlice: function (a, b, c) {
        isNaN(a) || (a = this.chartData[a]);
        a.pulled ? this.pullSlice(a, 0) : this.pullSlice(a, 1);
        AmCharts.getURL(a.url,
            this.urlTarget);
        c || (a = {type: "clickSlice", dataItem: a, chart: this, event: b}, this.fire(a.type, a))
    }, handleRightClick: function (a, b) {
        isNaN(a) || (a = this.chartData[a]);
        var c = {type: "rightClickSlice", dataItem: a, chart: this, event: b};
        this.fire(c.type, c)
    }, drawTicks: function () {
        var a = this.chartData, b;
        for (b = 0; b < a.length; b++) {
            var c = a[b];
            if (c.label) {
                var d = c.ty, d = AmCharts.line(this.container, [c.tx0, c.tx, c.tx2], [c.ty0, d, d], this.labelTickColor, this.labelTickAlpha);
                c.tick = d;
                c.wedge.push(d)
            }
        }
    }, initialStart: function () {
        var a =
            this, b = a.startDuration, c = setTimeout(function () {
            a.showLabels.call(a)
        }, 1E3 * b);
        a.timeOuts.push(c);
        a.chartCreated ? a.pullSlices(!0) : (a.startSlices(), 0 < b ? (b = setTimeout(function () {
            a.pullSlices.call(a)
        }, 1200 * b), a.timeOuts.push(b)) : a.pullSlices(!0))
    }, pullSlice: function (a, b, c) {
        var d = this.pullOutDuration;
        !0 === c && (d = 0);
        (c = a.wedge) && (0 < d ? c.animate({translate: b * a.pullX + "," + b * a.pullY}, d, this.pullOutEffect) : c.translate(b * a.pullX, b * a.pullY));
        1 == b ? (a.pulled = !0, this.pullOutOnlyOne && this.pullInAll(a.index), a = {
            type: "pullOutSlice",
            dataItem: a, chart: this
        }) : (a.pulled = !1, a = {type: "pullInSlice", dataItem: a, chart: this});
        this.fire(a.type, a)
    }, pullInAll: function (a) {
        var b = this.chartData, c;
        for (c = 0; c < this.chartData.length; c++)c != a && b[c].pulled && this.pullSlice(b[c], 0)
    }, pullOutAll: function (a) {
        a = this.chartData;
        var b;
        for (b = 0; b < a.length; b++)a[b].pulled || this.pullSlice(a[b], 1)
    }, parseData: function () {
        var a = [];
        this.chartData = a;
        var b = this.dataProvider;
        isNaN(this.pieAlpha) || (this.alpha = this.pieAlpha);
        if (void 0 !== b) {
            var c = b.length, d = 0, e, f, g;
            for (e = 0; e <
            c; e++) {
                f = {};
                var h = b[e];
                f.dataContext = h;
                f.value = Number(h[this.valueField]);
                (g = h[this.titleField]) || (g = "");
                f.title = g;
                f.pulled = AmCharts.toBoolean(h[this.pulledField], !1);
                (g = h[this.descriptionField]) || (g = "");
                f.description = g;
                f.labelRadius = Number(h[this.labelRadiusField]);
                f.switchable = !0;
                f.url = h[this.urlField];
                g = h[this.patternField];
                !g && this.patterns && (g = this.patterns[e]);
                f.pattern = g;
                f.visibleInLegend = AmCharts.toBoolean(h[this.visibleInLegendField], !0);
                g = h[this.alphaField];
                f.alpha = void 0 !== g ? Number(g) :
                    this.alpha;
                g = h[this.colorField];
                void 0 !== g && (f.color = AmCharts.toColor(g));
                f.labelColor = AmCharts.toColor(h[this.labelColorField]);
                d += f.value;
                f.hidden = !1;
                a[e] = f
            }
            for (e = b = 0; e < c; e++)f = a[e], f.percents = f.value / d * 100, f.percents < this.groupPercent && b++;
            1 < b && (this.groupValue = 0, this.removeSmallSlices(), a.push({
                title: this.groupedTitle,
                value: this.groupValue,
                percents: this.groupValue / d * 100,
                pulled: this.groupedPulled,
                color: this.groupedColor,
                url: this.groupedUrl,
                description: this.groupedDescription,
                alpha: this.groupedAlpha,
                pattern: this.groupedPattern,
                dataContext: {}
            }));
            c = this.baseColor;
            c || (c = this.pieBaseColor);
            d = this.brightnessStep;
            d || (d = this.pieBrightnessStep);
            for (e = 0; e < a.length; e++)c ? g = AmCharts.adjustLuminosity(c, e * d / 100) : (g = this.colors[e], void 0 === g && (g = AmCharts.randomColor())), void 0 === a[e].color && (a[e].color = g);
            this.recalculatePercents()
        }
    }, recalculatePercents: function () {
        var a = this.chartData, b = 0, c, d;
        for (c = 0; c < a.length; c++)d = a[c], !d.hidden && 0 < d.value && (b += d.value);
        for (c = 0; c < a.length; c++)d = this.chartData[c], d.percents =
            !d.hidden && 0 < d.value ? 100 * d.value / b : 0
    }, removeSmallSlices: function () {
        var a = this.chartData, b;
        for (b = a.length - 1; 0 <= b; b--)a[b].percents < this.groupPercent && (this.groupValue += a[b].value, a.splice(b, 1))
    }, animateAgain: function () {
        var a = this;
        a.startSlices();
        for (var b = 0; b < a.chartData.length; b++) {
            var c = a.chartData[b];
            c.started = !1;
            var d = c.wedge;
            d && d.translate(c.startX, c.startY)
        }
        b = a.startDuration;
        0 < b ? (b = setTimeout(function () {
            a.pullSlices.call(a)
        }, 1200 * b), a.timeOuts.push(b)) : a.pullSlices()
    }, measureMaxLabel: function () {
        var a =
            this.chartData, b = 0, c;
        for (c = 0; c < a.length; c++) {
            var d = a[c], e = this.formatString(this.labelText, d), f = this.labelFunction;
            f && (e = f(d, e));
            d = AmCharts.text(this.container, e, this.color, this.fontFamily, this.fontSize);
            e = d.getBBox().width;
            e > b && (b = e);
            d.remove()
        }
        return b
    }
});
AmCharts.AmRectangularChart = AmCharts.Class({
    inherits: AmCharts.AmCoordinateChart, construct: function (a) {
        AmCharts.AmRectangularChart.base.construct.call(this, a);
        this.theme = a;
        this.createEvents("zoomed");
        this.marginRight = this.marginBottom = this.marginTop = this.marginLeft = 20;
        this.verticalPosition = this.horizontalPosition = this.depth3D = this.angle = 0;
        this.heightMultiplier = this.widthMultiplier = 1;
        this.plotAreaFillColors = "#FFFFFF";
        this.plotAreaFillAlphas = 0;
        this.plotAreaBorderColor = "#000000";
        this.plotAreaBorderAlpha =
            0;
        this.zoomOutButtonImageSize = 17;
        this.zoomOutButtonImage = "lens.png";
        this.zoomOutText = "Show all";
        this.zoomOutButtonColor = "#e5e5e5";
        this.zoomOutButtonAlpha = 0;
        this.zoomOutButtonRollOverAlpha = 1;
        this.zoomOutButtonPadding = 8;
        this.trendLines = [];
        this.autoMargins = !0;
        this.marginsUpdated = !1;
        this.autoMarginOffset = 10;
        AmCharts.applyTheme(this, a, "AmRectangularChart")
    }, initChart: function () {
        AmCharts.AmRectangularChart.base.initChart.call(this);
        this.updateDxy();
        var a = !0;
        !this.marginsUpdated && this.autoMargins && (this.resetMargins(),
            a = !1);
        this.processScrollbars();
        this.updateMargins();
        this.updatePlotArea();
        this.updateScrollbars();
        this.updateTrendLines();
        this.updateChartCursor();
        this.updateValueAxes();
        a && (this.scrollbarOnly || this.updateGraphs())
    }, drawChart: function () {
        AmCharts.AmRectangularChart.base.drawChart.call(this);
        this.drawPlotArea();
        if (AmCharts.ifArray(this.chartData)) {
            var a = this.chartCursor;
            a && a.draw();
            a = this.zoomOutText;
            "" !== a && a && this.drawZoomOutButton()
        }
    }, resetMargins: function () {
        var a = {}, b;
        if ("serial" == this.type) {
            var c =
                this.valueAxes;
            for (b = 0; b < c.length; b++) {
                var d = c[b];
                d.ignoreAxisWidth || (d.setOrientation(this.rotate), d.fixAxisPosition(), a[d.position] = !0)
            }
            (b = this.categoryAxis) && !b.ignoreAxisWidth && (b.setOrientation(!this.rotate), b.fixAxisPosition(), b.fixAxisPosition(), a[b.position] = !0)
        } else {
            d = this.xAxes;
            c = this.yAxes;
            for (b = 0; b < d.length; b++) {
                var e = d[b];
                e.ignoreAxisWidth || (e.setOrientation(!0), e.fixAxisPosition(), a[e.position] = !0)
            }
            for (b = 0; b < c.length; b++)d = c[b], d.ignoreAxisWidth || (d.setOrientation(!1), d.fixAxisPosition(),
                a[d.position] = !0)
        }
        a.left && (this.marginLeft = 0);
        a.right && (this.marginRight = 0);
        a.top && (this.marginTop = 0);
        a.bottom && (this.marginBottom = 0);
        this.fixMargins = a
    }, measureMargins: function () {
        var a = this.valueAxes, b, c = this.autoMarginOffset, d = this.fixMargins, e = this.realWidth, f = this.realHeight, g = c, h = c, k = e;
        b = f;
        var l;
        for (l = 0; l < a.length; l++)b = this.getAxisBounds(a[l], g, k, h, b), g = Math.round(b.l), k = Math.round(b.r), h = Math.round(b.t), b = Math.round(b.b);
        if (a = this.categoryAxis)b = this.getAxisBounds(a, g, k, h, b), g = Math.round(b.l),
            k = Math.round(b.r), h = Math.round(b.t), b = Math.round(b.b);
        d.left && g < c && (this.marginLeft = Math.round(-g + c));
        d.right && k >= e - c && (this.marginRight = Math.round(k - e + c));
        d.top && h < c + this.titleHeight && (this.marginTop = Math.round(this.marginTop - h + c + this.titleHeight));
        d.bottom && b > f - c && (this.marginBottom = Math.round(this.marginBottom + b - f + c));
        this.initChart()
    }, getAxisBounds: function (a, b, c, d, e) {
        if (!a.ignoreAxisWidth) {
            var f = a.labelsSet, g = a.tickLength;
            a.inside && (g = 0);
            if (f)switch (f = a.getBBox(), a.position) {
                case "top":
                    a = f.y;
                    d > a && (d = a);
                    break;
                case "bottom":
                    a = f.y + f.height;
                    e < a && (e = a);
                    break;
                case "right":
                    a = f.x + f.width + g + 3;
                    c < a && (c = a);
                    break;
                case "left":
                    a = f.x - g, b > a && (b = a)
            }
        }
        return {l: b, t: d, r: c, b: e}
    }, drawZoomOutButton: function () {
        var a = this, b = a.container.set();
        a.zoomButtonSet.push(b);
        var c = a.color, d = a.fontSize, e = a.zoomOutButtonImageSize, f = a.zoomOutButtonImage, g = AmCharts.lang.zoomOutText || a.zoomOutText, h = a.zoomOutButtonColor, k = a.zoomOutButtonAlpha, l = a.zoomOutButtonFontSize, m = a.zoomOutButtonPadding;
        isNaN(l) || (d = l);
        (l = a.zoomOutButtonFontColor) &&
        (c = l);
        var l = a.zoomOutButton, n;
        l && (l.fontSize && (d = l.fontSize), l.color && (c = l.color), l.backgroundColor && (h = l.backgroundColor), isNaN(l.backgroundAlpha) || (a.zoomOutButtonRollOverAlpha = l.backgroundAlpha));
        var p = l = 0;
        void 0 !== a.pathToImages && f && (n = a.container.image(a.pathToImages + f, 0, 0, e, e), b.push(n), n = n.getBBox(), l = n.width + 5);
        void 0 !== g && (c = AmCharts.text(a.container, g, c, a.fontFamily, d, "start"), d = c.getBBox(), p = n ? n.height / 2 - 3 : d.height / 2, c.translate(l, p), b.push(c));
        n = b.getBBox();
        c = 1;
        AmCharts.isModern || (c = 0);
        h = AmCharts.rect(a.container, n.width + 2 * m + 5, n.height + 2 * m - 2, h, 1, 1, h, c);
        h.setAttr("opacity", k);
        h.translate(-m, -m);
        b.push(h);
        h.toBack();
        a.zbBG = h;
        n = h.getBBox();
        b.translate(a.marginLeftReal + a.plotAreaWidth - n.width + m, a.marginTopReal + m);
        b.hide();
        b.mouseover(function () {
            a.rollOverZB()
        }).mouseout(function () {
            a.rollOutZB()
        }).click(function () {
            a.clickZB()
        }).touchstart(function () {
            a.rollOverZB()
        }).touchend(function () {
            a.rollOutZB();
            a.clickZB()
        });
        for (k = 0; k < b.length; k++)b[k].attr({cursor: "pointer"});
        a.zbSet = b
    }, rollOverZB: function () {
        this.zbBG.setAttr("opacity",
            this.zoomOutButtonRollOverAlpha)
    }, rollOutZB: function () {
        this.zbBG.setAttr("opacity", this.zoomOutButtonAlpha)
    }, clickZB: function () {
        this.zoomOut()
    }, zoomOut: function () {
        this.updateScrollbar = !0;
        this.zoom()
    }, drawPlotArea: function () {
        var a = this.dx, b = this.dy, c = this.marginLeftReal, d = this.marginTopReal, e = this.plotAreaWidth - 1, f = this.plotAreaHeight - 1, g = this.plotAreaFillColors, h = this.plotAreaFillAlphas, k = this.plotAreaBorderColor, l = this.plotAreaBorderAlpha;
        this.trendLinesSet.clipRect(c, d, e, f);
        "object" == typeof h &&
        (h = h[0]);
        g = AmCharts.polygon(this.container, [0, e, e, 0, 0], [0, 0, f, f, 0], g, h, 1, k, l, this.plotAreaGradientAngle);
        g.translate(c + a, d + b);
        this.set.push(g);
        0 !== a && 0 !== b && (g = this.plotAreaFillColors, "object" == typeof g && (g = g[0]), g = AmCharts.adjustLuminosity(g, -.15), e = AmCharts.polygon(this.container, [0, a, e + a, e, 0], [0, b, b, 0, 0], g, h, 1, k, l), e.translate(c, d + f), this.set.push(e), a = AmCharts.polygon(this.container, [0, 0, a, a, 0], [0, f, f + b, b, 0], g, h, 1, k, l), a.translate(c, d), this.set.push(a));
        (c = this.bbset) && this.scrollbarOnly && c.remove()
    },
    updatePlotArea: function () {
        var a = this.updateWidth(), b = this.updateHeight(), c = this.container;
        this.realWidth = a;
        this.realWidth = b;
        c && this.container.setSize(a, b);
        a = a - this.marginLeftReal - this.marginRightReal - this.dx;
        b = b - this.marginTopReal - this.marginBottomReal;
        1 > a && (a = 1);
        1 > b && (b = 1);
        this.plotAreaWidth = Math.round(a);
        this.plotAreaHeight = Math.round(b)
    }, updateDxy: function () {
        this.dx = Math.round(this.depth3D * Math.cos(this.angle * Math.PI / 180));
        this.dy = Math.round(-this.depth3D * Math.sin(this.angle * Math.PI / 180));
        this.d3x =
            Math.round(this.columnSpacing3D * Math.cos(this.angle * Math.PI / 180));
        this.d3y = Math.round(-this.columnSpacing3D * Math.sin(this.angle * Math.PI / 180))
    }, updateMargins: function () {
        var a = this.getTitleHeight();
        this.titleHeight = a;
        this.marginTopReal = this.marginTop - this.dy + a;
        this.marginBottomReal = this.marginBottom;
        this.marginLeftReal = this.marginLeft;
        this.marginRightReal = this.marginRight
    }, updateValueAxes: function () {
        var a = this.valueAxes, b = this.marginLeftReal, c = this.marginTopReal, d = this.plotAreaHeight, e = this.plotAreaWidth,
            f;
        for (f = 0; f < a.length; f++) {
            var g = a[f];
            g.axisRenderer = AmCharts.RecAxis;
            g.guideFillRenderer = AmCharts.RecFill;
            g.axisItemRenderer = AmCharts.RecItem;
            g.dx = this.dx;
            g.dy = this.dy;
            g.viW = e - 1;
            g.viH = d - 1;
            g.marginsChanged = !0;
            g.viX = b;
            g.viY = c;
            this.updateObjectSize(g)
        }
    }, updateObjectSize: function (a) {
        a.width = (this.plotAreaWidth - 1) * this.widthMultiplier;
        a.height = (this.plotAreaHeight - 1) * this.heightMultiplier;
        a.x = this.marginLeftReal + this.horizontalPosition;
        a.y = this.marginTopReal + this.verticalPosition
    }, updateGraphs: function () {
        var a =
            this.graphs, b;
        for (b = 0; b < a.length; b++) {
            var c = a[b];
            c.x = this.marginLeftReal + this.horizontalPosition;
            c.y = this.marginTopReal + this.verticalPosition;
            c.width = this.plotAreaWidth * this.widthMultiplier;
            c.height = this.plotAreaHeight * this.heightMultiplier;
            c.index = b;
            c.dx = this.dx;
            c.dy = this.dy;
            c.rotate = this.rotate
        }
    }, updateChartCursor: function () {
        var a = this.chartCursor;
        a && (a = AmCharts.processObject(a, AmCharts.ChartCursor, this.theme), this.addChartCursor(a), a.x = this.marginLeftReal, a.y = this.marginTopReal, a.width = this.plotAreaWidth -
        1, a.height = this.plotAreaHeight - 1, a.chart = this)
    }, processScrollbars: function () {
        var a = this.chartScrollbar;
        a && (a = AmCharts.processObject(a, AmCharts.ChartScrollbar, this.theme), this.addChartScrollbar(a))
    }, updateScrollbars: function () {
    }, addChartCursor: function (a) {
        AmCharts.callMethod("destroy", [this.chartCursor]);
        a && (this.listenTo(a, "changed", this.handleCursorChange), this.listenTo(a, "zoomed", this.handleCursorZoom));
        this.chartCursor = a
    }, removeChartCursor: function () {
        AmCharts.callMethod("destroy", [this.chartCursor]);
        this.chartCursor = null
    }, zoomTrendLines: function () {
        var a = this.trendLines, b;
        for (b = 0; b < a.length; b++) {
            var c = a[b];
            c.valueAxis.recalculateToPercents ? c.set && c.set.hide() : (c.x = this.marginLeftReal + this.horizontalPosition, c.y = this.marginTopReal + this.verticalPosition, c.draw())
        }
    }, addTrendLine: function (a) {
        this.trendLines.push(a)
    }, removeTrendLine: function (a) {
        var b = this.trendLines, c;
        for (c = b.length - 1; 0 <= c; c--)b[c] == a && b.splice(c, 1)
    }, adjustMargins: function (a, b) {
        var c = a.scrollbarHeight + a.offset;
        "top" == a.position ? b ?
            this.marginLeftReal += c : this.marginTopReal += c : b ? this.marginRightReal += c : this.marginBottomReal += c
    }, getScrollbarPosition: function (a, b, c) {
        a.position = b ? "bottom" == c || "left" == c ? "bottom" : "top" : "top" == c || "right" == c ? "bottom" : "top"
    }, updateChartScrollbar: function (a, b) {
        if (a) {
            a.rotate = b;
            var c = this.marginTopReal, d = this.marginLeftReal, e = a.scrollbarHeight, f = this.dx, g = this.dy, h = a.offset;
            "top" == a.position ? b ? (a.y = c, a.x = d - e - h) : (a.y = c - e + g - 1 - h, a.x = d + f) : b ? (a.y = c + g, a.x = d + this.plotAreaWidth + f + h) : (a.y = c + this.plotAreaHeight +
            h, a.x = this.marginLeftReal)
        }
    }, showZB: function (a) {
        var b = this.zbSet;
        b && (a ? b.show() : b.hide(), this.rollOutZB())
    }, handleReleaseOutside: function (a) {
        AmCharts.AmRectangularChart.base.handleReleaseOutside.call(this, a);
        (a = this.chartCursor) && a.handleReleaseOutside()
    }, handleMouseDown: function (a) {
        AmCharts.AmRectangularChart.base.handleMouseDown.call(this, a);
        var b = this.chartCursor;
        b && b.handleMouseDown(a)
    }, handleCursorChange: function (a) {
    }
});
AmCharts.TrendLine = AmCharts.Class({
    construct: function (a) {
        this.cname = "TrendLine";
        this.createEvents("click");
        this.isProtected = !1;
        this.dashLength = 0;
        this.lineColor = "#00CC00";
        this.lineThickness = this.lineAlpha = 1;
        AmCharts.applyTheme(this, a, this.cname)
    }, draw: function () {
        var a = this;
        a.destroy();
        var b = a.chart, c = b.container, d, e, f, g, h = a.categoryAxis, k = a.initialDate, l = a.initialCategory, m = a.finalDate, n = a.finalCategory, p = a.valueAxis, q = a.valueAxisX, r = a.initialXValue, s = a.finalXValue, w = a.initialValue, v = a.finalValue,
            t = p.recalculateToPercents, u = b.dataDateFormat;
        h && (k && (k instanceof Date || (k = u ? AmCharts.stringToDate(k, u) : new Date(k)), a.initialDate = k, d = h.dateToCoordinate(k)), l && (d = h.categoryToCoordinate(l)), m && (m instanceof Date || (m = u ? AmCharts.stringToDate(m, u) : new Date(m)), a.finalDate = m, e = h.dateToCoordinate(m)), n && (e = h.categoryToCoordinate(n)));
        q && !t && (isNaN(r) || (d = q.getCoordinate(r)), isNaN(s) || (e = q.getCoordinate(s)));
        p && !t && (isNaN(w) || (f = p.getCoordinate(w)), isNaN(v) || (g = p.getCoordinate(v)));
        isNaN(d) || isNaN(e) ||
        isNaN(f) || isNaN(f) || (b.rotate ? (h = [f, g], e = [d, e]) : (h = [d, e], e = [f, g]), f = a.lineColor, d = AmCharts.line(c, h, e, f, a.lineAlpha, a.lineThickness, a.dashLength), g = h, k = e, n = h[1] - h[0], p = e[1] - e[0], 0 === n && (n = .01), 0 === p && (p = .01), l = n / Math.abs(n), m = p / Math.abs(p), p = n * p / Math.abs(n * p) * Math.sqrt(Math.pow(n, 2) + Math.pow(p, 2)), n = Math.asin(n / p), p = 90 * Math.PI / 180 - n, n = Math.abs(5 * Math.cos(p)), p = Math.abs(5 * Math.sin(p)), g.push(h[1] - l * p, h[0] - l * p), k.push(e[1] + m * n, e[0] + m * n), h = AmCharts.polygon(c, g, k, f, .005, 0), c = c.set([h, d]), c.translate(b.marginLeftReal,
            b.marginTopReal), b.trendLinesSet.push(c), a.line = d, a.set = c, h.mouseup(function () {
            a.handleLineClick()
        }).mouseover(function () {
            a.handleLineOver()
        }).mouseout(function () {
            a.handleLineOut()
        }), h.touchend && h.touchend(function () {
            a.handleLineClick()
        }))
    }, handleLineClick: function () {
        var a = {type: "click", trendLine: this, chart: this.chart};
        this.fire(a.type, a)
    }, handleLineOver: function () {
        var a = this.rollOverColor;
        void 0 !== a && this.line.attr({stroke: a})
    }, handleLineOut: function () {
        this.line.attr({stroke: this.lineColor})
    }, destroy: function () {
        AmCharts.remove(this.set)
    }
});
AmCharts.circle = function (a, b, c, d, e, f, g, h, k) {
    if (void 0 == e || 0 === e)e = .01;
    void 0 === f && (f = "#000000");
    void 0 === g && (g = 0);
    d = {fill: c, stroke: f, "fill-opacity": d, "stroke-width": e, "stroke-opacity": g};
    a = isNaN(k) ? a.circle(0, 0, b).attr(d) : a.ellipse(0, 0, b, k).attr(d);
    h && a.gradient("radialGradient", [c, AmCharts.adjustLuminosity(c, -.6)]);
    return a
};
AmCharts.text = function (a, b, c, d, e, f, g, h) {
    f || (f = "middle");
    "right" == f && (f = "end");
    isNaN(h) && (h = 1);
    void 0 !== b && (b = String(b), AmCharts.isIE && !AmCharts.isModern && (b = b.replace("&amp;", "&"), b = b.replace("&", "&amp;")));
    c = {fill: c, "font-family": d, "font-size": e, opacity: h};
    !0 === g && (c["font-weight"] = "bold");
    c["text-anchor"] = f;
    return a.text(b, c)
};
AmCharts.polygon = function (a, b, c, d, e, f, g, h, k, l, m) {
    isNaN(f) && (f = .01);
    isNaN(h) && (h = e);
    var n = d, p = !1;
    "object" == typeof n && 1 < n.length && (p = !0, n = n[0]);
    void 0 === g && (g = n);
    e = {fill: n, stroke: g, "fill-opacity": e, "stroke-width": f, "stroke-opacity": h};
    void 0 !== m && 0 < m && (e["stroke-dasharray"] = m);
    m = AmCharts.dx;
    f = AmCharts.dy;
    a.handDrawn && (c = AmCharts.makeHD(b, c, a.handDrawScatter), b = c[0], c = c[1]);
    g = Math.round;
    l && (g = AmCharts.doNothing);
    l = "M" + (g(b[0]) + m) + "," + (g(c[0]) + f);
    for (h = 1; h < b.length; h++)l += " L" + (g(b[h]) + m) + "," + (g(c[h]) +
    f);
    a = a.path(l + " Z").attr(e);
    p && a.gradient("linearGradient", d, k);
    return a
};
AmCharts.rect = function (a, b, c, d, e, f, g, h, k, l, m) {
    isNaN(f) && (f = 0);
    void 0 === k && (k = 0);
    void 0 === l && (l = 270);
    isNaN(e) && (e = 0);
    var n = d, p = !1;
    "object" == typeof n && (n = n[0], p = !0);
    void 0 === g && (g = n);
    void 0 === h && (h = e);
    b = Math.round(b);
    c = Math.round(c);
    var q = 0, r = 0;
    0 > b && (b = Math.abs(b), q = -b);
    0 > c && (c = Math.abs(c), r = -c);
    q += AmCharts.dx;
    r += AmCharts.dy;
    e = {fill: n, stroke: g, "fill-opacity": e, "stroke-opacity": h};
    void 0 !== m && 0 < m && (e["stroke-dasharray"] = m);
    a = a.rect(q, r, b, c, k, f).attr(e);
    p && a.gradient("linearGradient", d, l);
    return a
};
AmCharts.bullet = function (a, b, c, d, e, f, g, h, k, l, m) {
    var n;
    "circle" == b && (b = "round");
    switch (b) {
        case "round":
            n = AmCharts.circle(a, c / 2, d, e, f, g, h);
            break;
        case "square":
            n = AmCharts.polygon(a, [-c / 2, c / 2, c / 2, -c / 2], [c / 2, c / 2, -c / 2, -c / 2], d, e, f, g, h, l - 180);
            break;
        case "rectangle":
            n = AmCharts.polygon(a, [-c, c, c, -c], [c / 2, c / 2, -c / 2, -c / 2], d, e, f, g, h, l - 180);
            break;
        case "diamond":
            n = AmCharts.polygon(a, [-c / 2, 0, c / 2, 0], [0, -c / 2, 0, c / 2], d, e, f, g, h);
            break;
        case "triangleUp":
            n = AmCharts.triangle(a, c, 0, d, e, f, g, h);
            break;
        case "triangleDown":
            n = AmCharts.triangle(a,
                c, 180, d, e, f, g, h);
            break;
        case "triangleLeft":
            n = AmCharts.triangle(a, c, 270, d, e, f, g, h);
            break;
        case "triangleRight":
            n = AmCharts.triangle(a, c, 90, d, e, f, g, h);
            break;
        case "bubble":
            n = AmCharts.circle(a, c / 2, d, e, f, g, h, !0);
            break;
        case "line":
            n = AmCharts.line(a, [-c / 2, c / 2], [0, 0], d, e, f, g, h);
            break;
        case "yError":
            n = a.set();
            n.push(AmCharts.line(a, [0, 0], [-c / 2, c / 2], d, e, f));
            n.push(AmCharts.line(a, [-k, k], [-c / 2, -c / 2], d, e, f));
            n.push(AmCharts.line(a, [-k, k], [c / 2, c / 2], d, e, f));
            break;
        case "xError":
            n = a.set(), n.push(AmCharts.line(a, [-c /
            2, c / 2], [0, 0], d, e, f)), n.push(AmCharts.line(a, [-c / 2, -c / 2], [-k, k], d, e, f)), n.push(AmCharts.line(a, [c / 2, c / 2], [-k, k], d, e, f))
    }
    n && n.pattern(m);
    return n
};
AmCharts.triangle = function (a, b, c, d, e, f, g, h) {
    if (void 0 === f || 0 === f)f = 1;
    void 0 === g && (g = "#000");
    void 0 === h && (h = 0);
    d = {fill: d, stroke: g, "fill-opacity": e, "stroke-width": f, "stroke-opacity": h};
    b /= 2;
    var k;
    0 === c && (k = " M" + -b + "," + b + " L0," + -b + " L" + b + "," + b + " Z");
    180 == c && (k = " M" + -b + "," + -b + " L0," + b + " L" + b + "," + -b + " Z");
    90 == c && (k = " M" + -b + "," + -b + " L" + b + ",0 L" + -b + "," + b + " Z");
    270 == c && (k = " M" + -b + ",0 L" + b + "," + b + " L" + b + "," + -b + " Z");
    return a.path(k).attr(d)
};
AmCharts.line = function (a, b, c, d, e, f, g, h, k, l, m) {
    if (a.handDrawn && !m)return AmCharts.handDrawnLine(a, b, c, d, e, f, g, h, k, l, m);
    f = {fill: "none", "stroke-width": f};
    void 0 !== g && 0 < g && (f["stroke-dasharray"] = g);
    isNaN(e) || (f["stroke-opacity"] = e);
    d && (f.stroke = d);
    d = Math.round;
    l && (d = AmCharts.doNothing);
    l = AmCharts.dx;
    e = AmCharts.dy;
    g = "M" + (d(b[0]) + l) + "," + (d(c[0]) + e);
    for (h = 1; h < b.length; h++)g += " L" + (d(b[h]) + l) + "," + (d(c[h]) + e);
    if (AmCharts.VML)return a.path(g, void 0, !0).attr(f);
    k && (g += " M0,0 L0,0");
    return a.path(g).attr(f)
};
AmCharts.makeHD = function (a, b, c) {
    for (var d = [], e = [], f = 1; f < a.length; f++)for (var g = Number(a[f - 1]), h = Number(b[f - 1]), k = Number(a[f]), l = Number(b[f]), m = Math.sqrt(Math.pow(k - g, 2) + Math.pow(l - h, 2)), m = Math.round(m / 50) + 1, k = (k - g) / m, l = (l - h) / m, n = 0; n <= m; n++) {
        var p = g + n * k + Math.random() * c, q = h + n * l + Math.random() * c;
        d.push(p);
        e.push(q)
    }
    return [d, e]
};
AmCharts.handDrawnLine = function (a, b, c, d, e, f, g, h, k, l, m) {
    var n = a.set();
    for (m = 1; m < b.length; m++)for (var p = [b[m - 1], b[m]], q = [c[m - 1], c[m]], q = AmCharts.makeHD(p, q, a.handDrawScatter), p = q[0], q = q[1], r = 1; r < p.length; r++)n.push(AmCharts.line(a, [p[r - 1], p[r]], [q[r - 1], q[r]], d, e, f + Math.random() * a.handDrawThickness - a.handDrawThickness / 2, g, h, k, l, !0));
    return n
};
AmCharts.doNothing = function (a) {
    return a
};
AmCharts.wedge = function (a, b, c, d, e, f, g, h, k, l, m, n) {
    var p = Math.round;
    f = p(f);
    g = p(g);
    h = p(h);
    var q = p(g / f * h), r = AmCharts.VML, s = 359.5 + f / 100;
    359.94 < s && (s = 359.94);
    e >= s && (e = s);
    var w = 1 / 180 * Math.PI, s = b + Math.sin(d * w) * h, v = c - Math.cos(d * w) * q, t = b + Math.sin(d * w) * f, u = c - Math.cos(d * w) * g, x = b + Math.sin((d + e) * w) * f, E = c - Math.cos((d + e) * w) * g, A = b + Math.sin((d + e) * w) * h, w = c - Math.cos((d + e) * w) * q, z = {
        fill: AmCharts.adjustLuminosity(l.fill, -.2),
        "stroke-opacity": 0,
        "fill-opacity": l["fill-opacity"]
    }, H = 0;
    180 < Math.abs(e) && (H = 1);
    d = a.set();
    var F;
    r && (s = p(10 * s), t = p(10 * t), x = p(10 * x), A = p(10 * A), v = p(10 * v), u = p(10 * u), E = p(10 * E), w = p(10 * w), b = p(10 * b), k = p(10 * k), c = p(10 * c), f *= 10, g *= 10, h *= 10, q *= 10, 1 > Math.abs(e) && 1 >= Math.abs(x - t) && 1 >= Math.abs(E - u) && (F = !0));
    e = "";
    var G;
    n && (z["fill-opacity"] = 0, z["stroke-opacity"] = l["stroke-opacity"] / 2, z.stroke = l.stroke);
    0 < k && (G = " M" + s + "," + (v + k) + " L" + t + "," + (u + k), r ? (F || (G += " A" + (b - f) + "," + (k + c - g) + "," + (b + f) + "," + (k + c + g) + "," + t + "," + (u + k) + "," + x + "," + (E + k)), G += " L" + A + "," + (w + k), 0 < h && (F || (G += " B" + (b - h) + "," + (k + c - q) + "," + (b + h) + "," + (k + c + q) +
    "," + A + "," + (k + w) + "," + s + "," + (k + v)))) : (G += " A" + f + "," + g + ",0," + H + ",1," + x + "," + (E + k) + " L" + A + "," + (w + k), 0 < h && (G += " A" + h + "," + q + ",0," + H + ",0," + s + "," + (v + k))), G = a.path(G + " Z", void 0, void 0, "1000,1000").attr(z), d.push(G), G = a.path(" M" + s + "," + v + " L" + s + "," + (v + k) + " L" + t + "," + (u + k) + " L" + t + "," + u + " L" + s + "," + v + " Z", void 0, void 0, "1000,1000").attr(z), k = a.path(" M" + x + "," + E + " L" + x + "," + (E + k) + " L" + A + "," + (w + k) + " L" + A + "," + w + " L" + x + "," + E + " Z", void 0, void 0, "1000,1000").attr(z), d.push(G), d.push(k));
    r ? (F || (e = " A" + p(b - f) + "," + p(c - g) +
    "," + p(b + f) + "," + p(c + g) + "," + p(t) + "," + p(u) + "," + p(x) + "," + p(E)), f = " M" + p(s) + "," + p(v) + " L" + p(t) + "," + p(u) + e + " L" + p(A) + "," + p(w)) : f = " M" + s + "," + v + " L" + t + "," + u + (" A" + f + "," + g + ",0," + H + ",1," + x + "," + E) + " L" + A + "," + w;
    0 < h && (r ? F || (f += " B" + (b - h) + "," + (c - q) + "," + (b + h) + "," + (c + q) + "," + A + "," + w + "," + s + "," + v) : f += " A" + h + "," + q + ",0," + H + ",0," + s + "," + v);
    a.handDrawn && (b = AmCharts.line(a, [s, t], [v, u], l.stroke, l.thickness * Math.random() * a.handDrawThickness, l["stroke-opacity"]), d.push(b));
    a = a.path(f + " Z", void 0, void 0, "1000,1000").attr(l);
    if (m) {
        b = [];
        for (c = 0; c < m.length; c++)b.push(AmCharts.adjustLuminosity(l.fill, m[c]));
        0 < b.length && a.gradient("linearGradient", b)
    }
    a.pattern(n);
    d.push(a);
    return d
};
AmCharts.adjustLuminosity = function (a, b) {
    a = String(a).replace(/[^0-9a-f]/gi, "");
    6 > a.length && (a = String(a[0]) + String(a[0]) + String(a[1]) + String(a[1]) + String(a[2]) + String(a[2]));
    b = b || 0;
    var c = "#", d, e;
    for (e = 0; 3 > e; e++)d = parseInt(a.substr(2 * e, 2), 16), d = Math.round(Math.min(Math.max(0, d + d * b), 255)).toString(16), c += ("00" + d).substr(d.length);
    return c
};
AmCharts.Bezier = AmCharts.Class({
    construct: function (a, b, c, d, e, f, g, h, k, l) {
        "object" == typeof g && (g = g[0]);
        "object" == typeof h && (h = h[0]);
        f = {fill: g, "fill-opacity": h, "stroke-width": f};
        void 0 !== k && 0 < k && (f["stroke-dasharray"] = k);
        isNaN(e) || (f["stroke-opacity"] = e);
        d && (f.stroke = d);
        d = "M" + Math.round(b[0]) + "," + Math.round(c[0]);
        e = [];
        for (k = 0; k < b.length; k++)e.push({x: Number(b[k]), y: Number(c[k])});
        1 < e.length && (b = this.interpolate(e), d += this.drawBeziers(b));
        l ? d += l : AmCharts.VML || (d += "M0,0 L0,0");
        this.path = a.path(d).attr(f)
    },
    interpolate: function (a) {
        var b = [];
        b.push({x: a[0].x, y: a[0].y});
        var c = a[1].x - a[0].x, d = a[1].y - a[0].y, e = AmCharts.bezierX, f = AmCharts.bezierY;
        b.push({x: a[0].x + c / e, y: a[0].y + d / f});
        var g;
        for (g = 1; g < a.length - 1; g++) {
            var h = a[g - 1], k = a[g], d = a[g + 1];
            isNaN(d.x) && (d = k);
            isNaN(k.x) && (k = h);
            isNaN(h.x) && (h = k);
            c = d.x - k.x;
            d = d.y - h.y;
            h = k.x - h.x;
            h > c && (h = c);
            b.push({x: k.x - h / e, y: k.y - d / f});
            b.push({x: k.x, y: k.y});
            b.push({x: k.x + h / e, y: k.y + d / f})
        }
        d = a[a.length - 1].y - a[a.length - 2].y;
        c = a[a.length - 1].x - a[a.length - 2].x;
        b.push({
            x: a[a.length - 1].x -
            c / e, y: a[a.length - 1].y - d / f
        });
        b.push({x: a[a.length - 1].x, y: a[a.length - 1].y});
        return b
    }, drawBeziers: function (a) {
        var b = "", c;
        for (c = 0; c < (a.length - 1) / 3; c++)b += this.drawBezierMidpoint(a[3 * c], a[3 * c + 1], a[3 * c + 2], a[3 * c + 3]);
        return b
    }, drawBezierMidpoint: function (a, b, c, d) {
        var e = Math.round, f = this.getPointOnSegment(a, b, .75), g = this.getPointOnSegment(d, c, .75), h = (d.x - a.x) / 16, k = (d.y - a.y) / 16, l = this.getPointOnSegment(a, b, .375);
        a = this.getPointOnSegment(f, g, .375);
        a.x -= h;
        a.y -= k;
        b = this.getPointOnSegment(g, f, .375);
        b.x += h;
        b.y +=
            k;
        c = this.getPointOnSegment(d, c, .375);
        h = this.getMiddle(l, a);
        f = this.getMiddle(f, g);
        g = this.getMiddle(b, c);
        l = " Q" + e(l.x) + "," + e(l.y) + "," + e(h.x) + "," + e(h.y);
        l += " Q" + e(a.x) + "," + e(a.y) + "," + e(f.x) + "," + e(f.y);
        l += " Q" + e(b.x) + "," + e(b.y) + "," + e(g.x) + "," + e(g.y);
        return l += " Q" + e(c.x) + "," + e(c.y) + "," + e(d.x) + "," + e(d.y)
    }, getMiddle: function (a, b) {
        return {x: (a.x + b.x) / 2, y: (a.y + b.y) / 2}
    }, getPointOnSegment: function (a, b, c) {
        return {x: a.x + (b.x - a.x) * c, y: a.y + (b.y - a.y) * c}
    }
});
AmCharts.AmDraw = AmCharts.Class({
    construct: function (a, b, c, d) {
        AmCharts.SVG_NS = "http://www.w3.org/2000/svg";
        AmCharts.SVG_XLINK = "http://www.w3.org/1999/xlink";
        AmCharts.hasSVG = !!document.createElementNS && !!document.createElementNS(AmCharts.SVG_NS, "svg").createSVGRect;
        1 > b && (b = 10);
        1 > c && (c = 10);
        this.div = a;
        this.width = b;
        this.height = c;
        this.rBin = document.createElement("div");
        if (AmCharts.hasSVG) {
            AmCharts.SVG = !0;
            var e = this.createSvgElement("svg");
            e.style.position = "absolute";
            e.style.width = b + "px";
            e.style.height = c +
            "px";
            b = this.createSvgElement("desc");
            b.appendChild(document.createTextNode("JavaScript chart by amCharts " + d.version));
            e.appendChild(b);
            AmCharts.rtl && (e.setAttribute("direction", "rtl"), e.style.left = "auto", e.style.right = "0px");
            e.setAttribute("version", "1.1");
            a.appendChild(e);
            this.container = e;
            this.R = new AmCharts.SVGRenderer(this)
        } else AmCharts.isIE && AmCharts.VMLRenderer && (AmCharts.VML = !0, AmCharts.vmlStyleSheet || (document.namespaces.add("amvml", "urn:schemas-microsoft-com:vml"), 31 > document.styleSheets.length ?
            (e = document.createStyleSheet(), e.addRule(".amvml", "behavior:url(#default#VML); display:inline-block; antialias:true"), AmCharts.vmlStyleSheet = e) : document.styleSheets[0].addRule(".amvml", "behavior:url(#default#VML); display:inline-block; antialias:true")), this.container = a, this.R = new AmCharts.VMLRenderer(this, d), this.R.disableSelection(a))
    }, createSvgElement: function (a) {
        return document.createElementNS(AmCharts.SVG_NS, a)
    }, circle: function (a, b, c, d) {
        var e = new AmCharts.AmDObject("circle", this);
        e.attr({
            r: c,
            cx: a, cy: b
        });
        this.addToContainer(e.node, d);
        return e
    }, ellipse: function (a, b, c, d, e) {
        var f = new AmCharts.AmDObject("ellipse", this);
        f.attr({rx: c, ry: d, cx: a, cy: b});
        this.addToContainer(f.node, e);
        return f
    }, setSize: function (a, b) {
        0 < a && 0 < b && (this.container.style.width = a + "px", this.container.style.height = b + "px")
    }, rect: function (a, b, c, d, e, f, g) {
        var h = new AmCharts.AmDObject("rect", this);
        AmCharts.VML && (e = Math.round(100 * e / Math.min(c, d)), c += 2 * f, d += 2 * f, h.bw = f, h.node.style.marginLeft = -f, h.node.style.marginTop = -f);
        1 > c && (c =
            1);
        1 > d && (d = 1);
        h.attr({x: a, y: b, width: c, height: d, rx: e, ry: e, "stroke-width": f});
        this.addToContainer(h.node, g);
        return h
    }, image: function (a, b, c, d, e, f) {
        var g = new AmCharts.AmDObject("image", this);
        g.attr({x: b, y: c, width: d, height: e});
        this.R.path(g, a);
        this.addToContainer(g.node, f);
        return g
    }, addToContainer: function (a, b) {
        b || (b = this.container);
        b.appendChild(a)
    }, text: function (a, b, c) {
        return this.R.text(a, b, c)
    }, path: function (a, b, c, d) {
        var e = new AmCharts.AmDObject("path", this);
        d || (d = "100,100");
        e.attr({cs: d});
        c ? e.attr({dd: a}) :
            e.attr({d: a});
        this.addToContainer(e.node, b);
        return e
    }, set: function (a) {
        return this.R.set(a)
    }, remove: function (a) {
        if (a) {
            var b = this.rBin;
            b.appendChild(a);
            b.innerHTML = ""
        }
    }, renderFix: function () {
        var a = this.container, b = a.style, c;
        try {
            c = a.getScreenCTM() || a.createSVGMatrix()
        } catch (d) {
            c = a.createSVGMatrix()
        }
        a = 1 - c.e % 1;
        c = 1 - c.f % 1;
        .5 < a && (a -= 1);
        .5 < c && (c -= 1);
        a && (b.left = a + "px");
        c && (b.top = c + "px")
    }, update: function () {
        this.R.update()
    }
});
AmCharts.AmDObject = AmCharts.Class({
    construct: function (a, b) {
        this.D = b;
        this.R = b.R;
        this.node = this.R.create(this, a);
        this.y = this.x = 0;
        this.scale = 1
    }, attr: function (a) {
        this.R.attr(this, a);
        return this
    }, getAttr: function (a) {
        return this.node.getAttribute(a)
    }, setAttr: function (a, b) {
        this.R.setAttr(this, a, b);
        return this
    }, clipRect: function (a, b, c, d) {
        this.R.clipRect(this, a, b, c, d)
    }, translate: function (a, b, c, d) {
        d || (a = Math.round(a), b = Math.round(b));
        this.R.move(this, a, b, c);
        this.x = a;
        this.y = b;
        this.scale = c;
        this.angle && this.rotate(this.angle)
    },
    rotate: function (a, b) {
        this.R.rotate(this, a, b);
        this.angle = a
    }, animate: function (a, b, c) {
        for (var d in a)if (a.hasOwnProperty(d)) {
            var e = d, f = a[d];
            c = AmCharts.getEffect(c);
            this.R.animate(this, e, f, b, c)
        }
    }, push: function (a) {
        if (a) {
            var b = this.node;
            b.appendChild(a.node);
            var c = a.clipPath;
            c && b.appendChild(c);
            (a = a.grad) && b.appendChild(a)
        }
    }, text: function (a) {
        this.R.setText(this, a)
    }, remove: function () {
        this.R.remove(this)
    }, clear: function () {
        var a = this.node;
        if (a.hasChildNodes())for (; 1 <= a.childNodes.length;)a.removeChild(a.firstChild)
    },
    hide: function () {
        this.setAttr("visibility", "hidden")
    }, show: function () {
        this.setAttr("visibility", "visible")
    }, getBBox: function () {
        return this.R.getBBox(this)
    }, toFront: function () {
        var a = this.node;
        if (a) {
            this.prevNextNode = a.nextSibling;
            var b = a.parentNode;
            b && b.appendChild(a)
        }
    }, toPrevious: function () {
        var a = this.node;
        a && this.prevNextNode && (a = a.parentNode) && a.insertBefore(this.prevNextNode, null)
    }, toBack: function () {
        var a = this.node;
        if (a) {
            this.prevNextNode = a.nextSibling;
            var b = a.parentNode;
            if (b) {
                var c = b.firstChild;
                c && b.insertBefore(a, c)
            }
        }
    }, mouseover: function (a) {
        this.R.addListener(this, "mouseover", a);
        return this
    }, mouseout: function (a) {
        this.R.addListener(this, "mouseout", a);
        return this
    }, click: function (a) {
        this.R.addListener(this, "click", a);
        return this
    }, dblclick: function (a) {
        this.R.addListener(this, "dblclick", a);
        return this
    }, mousedown: function (a) {
        this.R.addListener(this, "mousedown", a);
        return this
    }, mouseup: function (a) {
        this.R.addListener(this, "mouseup", a);
        return this
    }, touchstart: function (a) {
        this.R.addListener(this,
            "touchstart", a);
        return this
    }, touchend: function (a) {
        this.R.addListener(this, "touchend", a);
        return this
    }, contextmenu: function (a) {
        this.node.addEventListener ? this.node.addEventListener("contextmenu", a, !0) : this.R.addListener(this, "contextmenu", a);
        return this
    }, stop: function (a) {
        (a = this.animationX) && AmCharts.removeFromArray(this.R.animations, a);
        (a = this.animationY) && AmCharts.removeFromArray(this.R.animations, a)
    }, length: function () {
        return this.node.childNodes.length
    }, gradient: function (a, b, c) {
        this.R.gradient(this,
            a, b, c)
    }, pattern: function (a, b) {
        a && this.R.pattern(this, a, b)
    }
});
AmCharts.VMLRenderer = AmCharts.Class({
    construct: function (a, b) {
        this.chart = b;
        this.D = a;
        this.cNames = {circle: "oval", ellipse: "oval", rect: "roundrect", path: "shape"};
        this.styleMap = {
            x: "left",
            y: "top",
            width: "width",
            height: "height",
            "font-family": "fontFamily",
            "font-size": "fontSize",
            visibility: "visibility"
        }
    }, create: function (a, b) {
        var c;
        if ("group" == b)c = document.createElement("div"), a.type = "div"; else if ("text" == b)c = document.createElement("div"), a.type = "text"; else if ("image" == b)c = document.createElement("img"), a.type =
            "image"; else {
            a.type = "shape";
            a.shapeType = this.cNames[b];
            c = document.createElement("amvml:" + this.cNames[b]);
            var d = document.createElement("amvml:stroke");
            c.appendChild(d);
            a.stroke = d;
            var e = document.createElement("amvml:fill");
            c.appendChild(e);
            a.fill = e;
            e.className = "amvml";
            d.className = "amvml";
            c.className = "amvml"
        }
        c.style.position = "absolute";
        c.style.top = 0;
        c.style.left = 0;
        return c
    }, path: function (a, b) {
        a.node.setAttribute("src", b)
    }, setAttr: function (a, b, c) {
        if (void 0 !== c) {
            var d;
            8 === document.documentMode && (d = !0);
            var e = a.node, f = a.type, g = e.style;
            "r" == b && (g.width = 2 * c, g.height = 2 * c);
            "oval" == a.shapeType && ("rx" == b && (g.width = 2 * c), "ry" == b && (g.height = 2 * c));
            "roundrect" != a.shapeType || "width" != b && "height" != b || (c -= 1);
            "cursor" == b && (g.cursor = c);
            "cx" == b && (g.left = c - AmCharts.removePx(g.width) / 2);
            "cy" == b && (g.top = c - AmCharts.removePx(g.height) / 2);
            var h = this.styleMap[b];
            void 0 !== h && (g[h] = c);
            "text" == f && ("text-anchor" == b && (a.anchor = c, h = e.clientWidth, "end" == c && (g.marginLeft = -h + "px"), "middle" == c && (g.marginLeft = -(h / 2) + "px", g.textAlign =
                "center"), "start" == c && (g.marginLeft = "0px")), "fill" == b && (g.color = c), "font-weight" == b && (g.fontWeight = c));
            if (g = a.children)for (h = 0; h < g.length; h++)g[h].setAttr(b, c);
            if ("shape" == f) {
                "cs" == b && (e.style.width = "100px", e.style.height = "100px", e.setAttribute("coordsize", c));
                "d" == b && e.setAttribute("path", this.svgPathToVml(c));
                "dd" == b && e.setAttribute("path", c);
                f = a.stroke;
                a = a.fill;
                "stroke" == b && (d ? f.color = c : f.setAttribute("color", c));
                "stroke-width" == b && (d ? f.weight = c : f.setAttribute("weight", c));
                "stroke-opacity" == b &&
                (d ? f.opacity = c : f.setAttribute("opacity", c));
                "stroke-dasharray" == b && (g = "solid", 0 < c && 3 > c && (g = "dot"), 3 <= c && 6 >= c && (g = "dash"), 6 < c && (g = "longdash"), d ? f.dashstyle = g : f.setAttribute("dashstyle", g));
                if ("fill-opacity" == b || "opacity" == b)0 === c ? d ? a.on = !1 : a.setAttribute("on", !1) : d ? a.opacity = c : a.setAttribute("opacity", c);
                "fill" == b && (d ? a.color = c : a.setAttribute("color", c));
                "rx" == b && (d ? e.arcSize = c + "%" : e.setAttribute("arcsize", c + "%"))
            }
        }
    }, attr: function (a, b) {
        for (var c in b)b.hasOwnProperty(c) && this.setAttr(a, c, b[c])
    }, text: function (a,
                       b, c) {
        var d = new AmCharts.AmDObject("text", this.D), e = d.node;
        e.style.whiteSpace = "pre";
        e.innerHTML = a;
        this.D.addToContainer(e, c);
        this.attr(d, b);
        return d
    }, getBBox: function (a) {
        return this.getBox(a.node)
    }, getBox: function (a) {
        var b = a.offsetLeft, c = a.offsetTop, d = a.offsetWidth, e = a.offsetHeight, f;
        if (a.hasChildNodes()) {
            var g, h, k;
            for (k = 0; k < a.childNodes.length; k++) {
                f = this.getBox(a.childNodes[k]);
                var l = f.x;
                isNaN(l) || (isNaN(g) ? g = l : l < g && (g = l));
                var m = f.y;
                isNaN(m) || (isNaN(h) ? h = m : m < h && (h = m));
                l = f.width + l;
                isNaN(l) || (d = Math.max(d,
                    l));
                f = f.height + m;
                isNaN(f) || (e = Math.max(e, f))
            }
            0 > g && (b += g);
            0 > h && (c += h)
        }
        return {x: b, y: c, width: d, height: e}
    }, setText: function (a, b) {
        var c = a.node;
        c && (c.innerHTML = b);
        this.setAttr(a, "text-anchor", a.anchor)
    }, addListener: function (a, b, c) {
        a.node["on" + b] = c
    }, move: function (a, b, c) {
        var d = a.node, e = d.style;
        "text" == a.type && (c -= AmCharts.removePx(e.fontSize) / 2 - 1);
        "oval" == a.shapeType && (b -= AmCharts.removePx(e.width) / 2, c -= AmCharts.removePx(e.height) / 2);
        a = a.bw;
        isNaN(a) || (b -= a, c -= a);
        isNaN(b) || isNaN(c) || (d.style.left = b + "px", d.style.top =
            c + "px")
    }, svgPathToVml: function (a) {
        var b = a.split(" ");
        a = "";
        var c, d = Math.round, e;
        for (e = 0; e < b.length; e++) {
            var f = b[e], g = f.substring(0, 1), f = f.substring(1), h = f.split(","), k = d(h[0]) + "," + d(h[1]);
            "M" == g && (a += " m " + k);
            "L" == g && (a += " l " + k);
            "Z" == g && (a += " x e");
            if ("Q" == g) {
                var l = c.length, m = c[l - 1], n = h[0], p = h[1], k = h[2], q = h[3];
                c = d(c[l - 2] / 3 + 2 / 3 * n);
                m = d(m / 3 + 2 / 3 * p);
                n = d(2 / 3 * n + k / 3);
                p = d(2 / 3 * p + q / 3);
                a += " c " + c + "," + m + "," + n + "," + p + "," + k + "," + q
            }
            "A" == g && (a += " wa " + f);
            "B" == g && (a += " at " + f);
            c = h
        }
        return a
    }, animate: function (a, b, c, d,
                          e) {
        var f = a.node, g = this.chart;
        if ("translate" == b) {
            b = c.split(",");
            c = b[1];
            var h = f.offsetTop;
            g.animate(a, "left", f.offsetLeft, b[0], d, e, "px");
            g.animate(a, "top", h, c, d, e, "px")
        }
    }, clipRect: function (a, b, c, d, e) {
        a = a.node;
        0 === b && 0 === c ? (a.style.width = d + "px", a.style.height = e + "px", a.style.overflow = "hidden") : a.style.clip = "rect(" + c + "px " + (b + d) + "px " + (c + e) + "px " + b + "px)"
    }, rotate: function (a, b, c) {
        if (0 !== Number(b)) {
            var d = a.node;
            a = d.style;
            c || (c = this.getBGColor(d.parentNode));
            a.backgroundColor = c;
            a.paddingLeft = 1;
            c = b * Math.PI /
            180;
            var e = Math.cos(c), f = Math.sin(c), g = AmCharts.removePx(a.left), h = AmCharts.removePx(a.top), k = d.offsetWidth, d = d.offsetHeight;
            b /= Math.abs(b);
            a.left = g + k / 2 - k / 2 * Math.cos(c) - b * d / 2 * Math.sin(c) + 3;
            a.top = h - b * k / 2 * Math.sin(c) + b * d / 2 * Math.sin(c);
            a.cssText = a.cssText + "; filter:progid:DXImageTransform.Microsoft.Matrix(M11='" + e + "', M12='" + -f + "', M21='" + f + "', M22='" + e + "', sizingmethod='auto expand');"
        }
    }, getBGColor: function (a) {
        var b = "#FFFFFF";
        if (a.style) {
            var c = a.style.backgroundColor;
            "" !== c ? b = c : a.parentNode && (b = this.getBGColor(a.parentNode))
        }
        return b
    },
    set: function (a) {
        var b = new AmCharts.AmDObject("group", this.D);
        this.D.container.appendChild(b.node);
        if (a) {
            var c;
            for (c = 0; c < a.length; c++)b.push(a[c])
        }
        return b
    }, gradient: function (a, b, c, d) {
        var e = "";
        "radialGradient" == b && (b = "gradientradial", c.reverse());
        "linearGradient" == b && (b = "gradient");
        var f;
        for (f = 0; f < c.length; f++) {
            var g = Math.round(100 * f / (c.length - 1)), e = e + (g + "% " + c[f]);
            f < c.length - 1 && (e += ",")
        }
        a = a.fill;
        90 == d ? d = 0 : 270 == d ? d = 180 : 180 == d ? d = 90 : 0 === d && (d = 270);
        8 === document.documentMode ? (a.type = b, a.angle = d) : (a.setAttribute("type",
            b), a.setAttribute("angle", d));
        e && (a.colors.value = e)
    }, remove: function (a) {
        a.clipPath && this.D.remove(a.clipPath);
        this.D.remove(a.node)
    }, disableSelection: function (a) {
        void 0 !== typeof a.onselectstart && (a.onselectstart = function () {
            return !1
        });
        a.style.cursor = "default"
    }, pattern: function (a, b) {
        var c = a.node, d = a.fill, e = "none";
        b.color && (e = b.color);
        c.fillColor = e;
        8 === document.documentMode ? (d.type = "tile", d.src = b.url) : (d.setAttribute("type", "tile"), d.setAttribute("src", b.url))
    }, update: function () {
    }
});
AmCharts.SVGRenderer = AmCharts.Class({
    construct: function (a) {
        this.D = a;
        this.animations = []
    }, create: function (a, b) {
        return document.createElementNS(AmCharts.SVG_NS, b)
    }, attr: function (a, b) {
        for (var c in b)b.hasOwnProperty(c) && this.setAttr(a, c, b[c])
    }, setAttr: function (a, b, c) {
        void 0 !== c && a.node.setAttribute(b, c)
    }, animate: function (a, b, c, d, e) {
        var f = a.node;
        a["an_" + b] && AmCharts.removeFromArray(this.animations, a["an_" + b]);
        "translate" == b ? (f = (f = f.getAttribute("transform")) ? String(f).substring(10, f.length - 1) : "0,0", f =
            f.split(", ").join(" "), f = f.split(" ").join(","), 0 === f && (f = "0,0")) : f = Number(f.getAttribute(b));
        c = {obj: a, frame: 0, attribute: b, from: f, to: c, time: d, effect: e};
        this.animations.push(c);
        a["an_" + b] = c
    }, update: function () {
        var a, b = this.animations;
        for (a = b.length - 1; 0 <= a; a--) {
            var c = b[a], d = 1E3 * c.time / AmCharts.updateRate, e = c.frame + 1, f = c.obj, g = c.attribute, h, k, l;
            e <= d ? (c.frame++, "translate" == g ? (h = c.from.split(","), g = Number(h[0]), h = Number(h[1]), isNaN(h) && (h = 0), k = c.to.split(","), l = Number(k[0]), k = Number(k[1]), l = 0 === l - g ? l :
                Math.round(AmCharts[c.effect](0, e, g, l - g, d)), c = 0 === k - h ? k : Math.round(AmCharts[c.effect](0, e, h, k - h, d)), g = "transform", c = "translate(" + l + "," + c + ")") : (k = Number(c.from), h = Number(c.to), l = h - k, c = AmCharts[c.effect](0, e, k, l, d), isNaN(c) && (c = h), 0 === l && this.animations.splice(a, 1)), this.setAttr(f, g, c)) : ("translate" == g ? (k = c.to.split(","), l = Number(k[0]), k = Number(k[1]), f.translate(l, k)) : (h = Number(c.to), this.setAttr(f, g, h)), this.animations.splice(a, 1))
        }
    }, getBBox: function (a) {
        if (a = a.node)try {
            return a.getBBox()
        } catch (b) {
        }
        return {
            width: 0,
            height: 0, x: 0, y: 0
        }
    }, path: function (a, b) {
        a.node.setAttributeNS(AmCharts.SVG_XLINK, "xlink:href", b)
    }, clipRect: function (a, b, c, d, e) {
        var f = a.node, g = a.clipPath;
        g && this.D.remove(g);
        var h = f.parentNode;
        h && (f = document.createElementNS(AmCharts.SVG_NS, "clipPath"), g = AmCharts.getUniqueId(), f.setAttribute("id", g), this.D.rect(b, c, d, e, 0, 0, f), h.appendChild(f), b = "#", AmCharts.baseHref && !AmCharts.isIE && (b = window.location.href + b), this.setAttr(a, "clip-path", "url(" + b + g + ")"), this.clipPathC++, a.clipPath = f)
    }, text: function (a, b,
                       c) {
        var d = new AmCharts.AmDObject("text", this.D);
        a = String(a).split("\n");
        var e = b["font-size"], f;
        for (f = 0; f < a.length; f++) {
            var g = this.create(null, "tspan");
            g.appendChild(document.createTextNode(a[f]));
            g.setAttribute("y", (e + 2) * f + Math.round(e / 2));
            g.setAttribute("x", 0);
            d.node.appendChild(g)
        }
        d.node.setAttribute("y", Math.round(e / 2));
        this.attr(d, b);
        this.D.addToContainer(d.node, c);
        return d
    }, setText: function (a, b) {
        var c = a.node;
        c && (c.removeChild(c.firstChild), c.appendChild(document.createTextNode(b)))
    }, move: function (a,
                       b, c, d) {
        b = "translate(" + b + "," + c + ")";
        d && (b = b + " scale(" + d + ")");
        this.setAttr(a, "transform", b)
    }, rotate: function (a, b) {
        var c = a.node.getAttribute("transform"), d = "rotate(" + b + ")";
        c && (d = c + " " + d);
        this.setAttr(a, "transform", d)
    }, set: function (a) {
        var b = new AmCharts.AmDObject("g", this.D);
        this.D.container.appendChild(b.node);
        if (a) {
            var c;
            for (c = 0; c < a.length; c++)b.push(a[c])
        }
        return b
    }, addListener: function (a, b, c) {
        a.node["on" + b] = c
    }, gradient: function (a, b, c, d) {
        var e = a.node, f = a.grad;
        f && this.D.remove(f);
        b = document.createElementNS(AmCharts.SVG_NS,
            b);
        f = AmCharts.getUniqueId();
        b.setAttribute("id", f);
        if (!isNaN(d)) {
            var g = 0, h = 0, k = 0, l = 0;
            90 == d ? k = 100 : 270 == d ? l = 100 : 180 == d ? g = 100 : 0 === d && (h = 100);
            b.setAttribute("x1", g + "%");
            b.setAttribute("x2", h + "%");
            b.setAttribute("y1", k + "%");
            b.setAttribute("y2", l + "%")
        }
        for (d = 0; d < c.length; d++)g = document.createElementNS(AmCharts.SVG_NS, "stop"), h = 100 * d / (c.length - 1), 0 === d && (h = 0), g.setAttribute("offset", h + "%"), g.setAttribute("stop-color", c[d]), b.appendChild(g);
        e.parentNode.appendChild(b);
        c = "#";
        AmCharts.baseHref && !AmCharts.isIE &&
        (c = window.location.href + c);
        e.setAttribute("fill", "url(" + c + f + ")");
        a.grad = b
    }, pattern: function (a, b, c) {
        var d = a.node;
        isNaN(c) && (c = 1);
        var e = a.patternNode;
        e && this.D.remove(e);
        var e = document.createElementNS(AmCharts.SVG_NS, "pattern"), f = AmCharts.getUniqueId(), g = b;
        b.url && (g = b.url);
        var h = Number(b.width);
        isNaN(h) && (h = 4);
        var k = Number(b.height);
        isNaN(k) && (k = 4);
        h /= c;
        k /= c;
        c = b.x;
        isNaN(c) && (c = 0);
        var l = -Math.random() * Number(b.randomX);
        isNaN(l) || (c = l);
        l = b.y;
        isNaN(l) && (l = 0);
        var m = -Math.random() * Number(b.randomY);
        isNaN(m) ||
        (l = m);
        e.setAttribute("id", f);
        e.setAttribute("width", h);
        e.setAttribute("height", k);
        e.setAttribute("patternUnits", "userSpaceOnUse");
        e.setAttribute("xlink:href", g);
        b.color && (m = document.createElementNS(AmCharts.SVG_NS, "rect"), m.setAttributeNS(null, "height", h), m.setAttributeNS(null, "width", k), m.setAttributeNS(null, "fill", b.color), e.appendChild(m));
        this.D.image(g, 0, 0, h, k, e).translate(c, l);
        g = "#";
        AmCharts.baseHref && !AmCharts.isIE && (g = window.location.href + g);
        d.setAttribute("fill", "url(" + g + f + ")");
        a.patternNode =
            e;
        d.parentNode.appendChild(e)
    }, remove: function (a) {
        a.clipPath && this.D.remove(a.clipPath);
        a.grad && this.D.remove(a.grad);
        a.patternNode && this.D.remove(a.patternNode);
        this.D.remove(a.node)
    }
});
AmCharts.AmDSet = AmCharts.Class({
    construct: function (a) {
        this.create("g")
    }, attr: function (a) {
        this.R.attr(this.node, a)
    }, move: function (a, b) {
        this.R.move(this.node, a, b)
    }
});
AmCharts.AmLegend = AmCharts.Class({
    construct: function (a) {
        this.cname = "AmLegend";
        this.createEvents("rollOverMarker", "rollOverItem", "rollOutMarker", "rollOutItem", "showItem", "hideItem", "clickMarker", "rollOverItem", "rollOutItem", "clickLabel");
        this.position = "bottom";
        this.borderColor = this.color = "#000000";
        this.borderAlpha = 0;
        this.markerLabelGap = 5;
        this.verticalGap = 10;
        this.align = "left";
        this.horizontalGap = 0;
        this.spacing = 10;
        this.markerDisabledColor = "#AAB3B3";
        this.markerType = "square";
        this.markerSize = 16;
        this.markerBorderThickness =
            this.markerBorderAlpha = 1;
        this.marginBottom = this.marginTop = 0;
        this.marginLeft = this.marginRight = 20;
        this.autoMargins = !0;
        this.valueWidth = 50;
        this.switchable = !0;
        this.switchType = "x";
        this.switchColor = "#FFFFFF";
        this.rollOverColor = "#CC0000";
        this.reversedOrder = !1;
        this.labelText = "[[title]]";
        this.valueText = "[[value]]";
        this.useMarkerColorForLabels = !1;
        this.rollOverGraphAlpha = 1;
        this.textClickEnabled = !1;
        this.equalWidths = !0;
        this.dateFormat = "DD-MM-YYYY";
        this.backgroundColor = "#FFFFFF";
        this.backgroundAlpha = 0;
        this.useGraphSettings = !1;
        this.showEntries = !0;
        AmCharts.applyTheme(this, a, this.cname)
    }, setData: function (a) {
        this.legendData = a;
        this.invalidateSize()
    }, invalidateSize: function () {
        this.destroy();
        this.entries = [];
        this.valueLabels = [];
        (AmCharts.ifArray(this.legendData) || AmCharts.ifArray(this.data)) && this.drawLegend()
    }, drawLegend: function () {
        var a = this.chart, b = this.position, c = this.width, d = a.divRealWidth, e = a.divRealHeight, f = this.div, g = this.legendData;
        this.data && (g = this.data);
        isNaN(this.fontSize) && (this.fontSize = a.fontSize);
        if ("right" ==
            b || "left" == b)this.maxColumns = 1, this.autoMargins && (this.marginLeft = this.marginRight = 10); else if (this.autoMargins) {
            this.marginRight = a.marginRight;
            this.marginLeft = a.marginLeft;
            var h = a.autoMarginOffset;
            "bottom" == b ? (this.marginBottom = h, this.marginTop = 0) : (this.marginTop = h, this.marginBottom = 0)
        }
        var k;
        void 0 !== c ? k = AmCharts.toCoordinate(c, d) : "right" != b && "left" != b && (k = a.realWidth);
        "outside" == b ? (k = f.offsetWidth, e = f.offsetHeight, f.clientHeight && (k = f.clientWidth, e = f.clientHeight)) : (isNaN(k) || (f.style.width = k + "px"),
            f.className = "amChartsLegend");
        this.divWidth = k;
        (b = this.container) ? (b.container.innerHTML = "", f.appendChild(b.container), b.setSize(k, e)) : b = new AmCharts.AmDraw(f, k, e, a);
        this.container = b;
        this.lx = 0;
        this.ly = 8;
        e = this.markerSize;
        e > this.fontSize && (this.ly = e / 2 - 1);
        0 < e && (this.lx += e + this.markerLabelGap);
        this.titleWidth = 0;
        if (e = this.title)a = AmCharts.text(this.container, e, this.color, a.fontFamily, this.fontSize, "start", !0), a.translate(this.marginLeft, this.marginTop + this.verticalGap + this.ly + 1), a = a.getBBox(), this.titleWidth =
            a.width + 15, this.titleHeight = a.height + 6;
        this.index = this.maxLabelWidth = 0;
        if (this.showEntries) {
            for (a = 0; a < g.length; a++)this.createEntry(g[a]);
            for (a = this.index = 0; a < g.length; a++)this.createValue(g[a])
        }
        this.arrangeEntries();
        this.updateValues()
    }, arrangeEntries: function () {
        var a = this.position, b = this.marginLeft + this.titleWidth, c = this.marginRight, d = this.marginTop, e = this.marginBottom, f = this.horizontalGap, g = this.div, h = this.divWidth, k = this.maxColumns, l = this.verticalGap, m = this.spacing, n = h - c - b, p = 0, q = 0, r = this.container;
        this.set && this.set.remove();
        var s = r.set();
        this.set = s;
        r = r.set();
        s.push(r);
        var w = this.entries, v, t;
        for (t = 0; t < w.length; t++) {
            v = w[t].getBBox();
            var u = v.width;
            u > p && (p = u);
            v = v.height;
            v > q && (q = v)
        }
        var u = q = 0, x = f, E = 0, A = 0;
        for (t = 0; t < w.length; t++) {
            var z = w[t];
            this.reversedOrder && (z = w[w.length - t - 1]);
            v = z.getBBox();
            var H;
            this.equalWidths ? H = f + u * (p + m + this.markerLabelGap) : (H = x, x = x + v.width + f + m);
            v.height > A && (A = v.height);
            H + v.width > n && 0 < t && 0 !== u && (q++, u = 0, H = f, x = H + v.width + f + m, E = E + A + l, A = 0);
            z.translate(H, E);
            u++;
            !isNaN(k) && u >= k &&
            (u = 0, q++, E = E + A + l, A = 0);
            r.push(z)
        }
        v = r.getBBox();
        k = v.height + 2 * l - 1;
        "left" == a || "right" == a ? (h = v.width + 2 * f, g.style.width = h + b + c + "px") : h = h - b - c - 1;
        c = AmCharts.polygon(this.container, [0, h, h, 0], [0, 0, k, k], this.backgroundColor, this.backgroundAlpha, 1, this.borderColor, this.borderAlpha);
        s.push(c);
        s.translate(b, d);
        c.toBack();
        b = f;
        if ("top" == a || "bottom" == a || "absolute" == a || "outside" == a)"center" == this.align ? b = f + (h - v.width) / 2 : "right" == this.align && (b = f + h - v.width);
        r.translate(b, l + 1);
        this.titleHeight > k && (k = this.titleHeight);
        a = k + d + e + 1;
        0 > a && (a = 0);
        g.style.height = Math.round(a) + "px"
    }, createEntry: function (a) {
        if (!1 !== a.visibleInLegend) {
            var b = this.chart, c = a.markerType;
            c || (c = this.markerType);
            var d = a.color, e = a.alpha;
            a.legendKeyColor && (d = a.legendKeyColor());
            a.legendKeyAlpha && (e = a.legendKeyAlpha());
            var f;
            !0 === a.hidden && (f = d = this.markerDisabledColor);
            var g = a.pattern, h = a.customMarker;
            h || (h = this.customMarker);
            var k = this.container, l = this.markerSize, m = 0, n = 0, p = l / 2;
            if (this.useGraphSettings)if (m = a.type, this.switchType = void 0, "line" == m ||
                "step" == m || "smoothedLine" == m || "ohlc" == m)g = k.set(), a.hidden || (d = a.lineColorR, f = a.bulletBorderColorR), n = AmCharts.line(k, [0, 2 * l], [l / 2, l / 2], d, a.lineAlpha, a.lineThickness, a.dashLength), g.push(n), a.bullet && (a.hidden || (d = a.bulletColorR), n = AmCharts.bullet(k, a.bullet, a.bulletSize, d, a.bulletAlpha, a.bulletBorderThickness, f, a.bulletBorderAlpha)) && (n.translate(l + 1, l / 2), g.push(n)), p = 0, m = l, n = l / 3; else {
                var q;
                a.getGradRotation && (q = a.getGradRotation());
                m = a.fillColorsR;
                !0 === a.hidden && (m = d);
                if (g = this.createMarker("rectangle",
                        m, a.fillAlphas, a.lineThickness, d, a.lineAlpha, q, g))p = l, g.translate(p, l / 2);
                m = l
            } else h ? (b.path && (h = b.path + h), g = k.image(h, 0, 0, l, l)) : (g = this.createMarker(c, d, e, void 0, void 0, void 0, void 0, g)) && g.translate(l / 2, l / 2);
            this.addListeners(g, a);
            k = k.set([g]);
            this.switchable && a.switchable && k.setAttr("cursor", "pointer");
            (f = this.switchType) && "none" != f && ("x" == f ? (q = this.createX(), q.translate(l / 2, l / 2)) : q = this.createV(), q.dItem = a, !0 !== a.hidden ? "x" == f ? q.hide() : q.show() : "x" != f && q.hide(), this.switchable || q.hide(), this.addListeners(q,
                a), a.legendSwitch = q, k.push(q));
            f = this.color;
            a.showBalloon && this.textClickEnabled && void 0 !== this.selectedColor && (f = this.selectedColor);
            this.useMarkerColorForLabels && (f = d);
            !0 === a.hidden && (f = this.markerDisabledColor);
            d = AmCharts.massReplace(this.labelText, {"[[title]]": a.title});
            q = this.fontSize;
            g && l <= q && g.translate(p, l / 2 + this.ly - q / 2 + (q + 2 - l) / 2 - n);
            var r;
            d && (d = AmCharts.fixBrakes(d), a.legendTextReal = d, r = this.labelWidth, r = isNaN(r) ? AmCharts.text(this.container, d, f, b.fontFamily, q, "start") : AmCharts.wrappedText(this.container,
                d, f, b.fontFamily, q, "start", !1, r, 0), r.translate(this.lx + m, this.ly), k.push(r), b = r.getBBox().width, this.maxLabelWidth < b && (this.maxLabelWidth = b));
            this.entries[this.index] = k;
            a.legendEntry = this.entries[this.index];
            a.legendLabel = r;
            this.index++
        }
    }, addListeners: function (a, b) {
        var c = this;
        a && a.mouseover(function (a) {
            c.rollOverMarker(b, a)
        }).mouseout(function (a) {
            c.rollOutMarker(b, a)
        }).click(function (a) {
            c.clickMarker(b, a)
        })
    }, rollOverMarker: function (a, b) {
        this.switchable && this.dispatch("rollOverMarker", a, b);
        this.dispatch("rollOverItem",
            a, b)
    }, rollOutMarker: function (a, b) {
        this.switchable && this.dispatch("rollOutMarker", a, b);
        this.dispatch("rollOutItem", a, b)
    }, clickMarker: function (a, b) {
        this.switchable && (!0 === a.hidden ? this.dispatch("showItem", a, b) : this.dispatch("hideItem", a, b));
        this.dispatch("clickMarker", a, b)
    }, rollOverLabel: function (a, b) {
        a.hidden || (this.textClickEnabled && a.legendLabel && a.legendLabel.attr({fill: this.rollOverColor}), this.dispatch("rollOverItem", a, b))
    }, rollOutLabel: function (a, b) {
        if (!a.hidden) {
            if (this.textClickEnabled && a.legendLabel) {
                var c =
                    this.color;
                void 0 !== this.selectedColor && a.showBalloon && (c = this.selectedColor);
                this.useMarkerColorForLabels && (c = a.lineColor, void 0 === c && (c = a.color));
                a.legendLabel.attr({fill: c})
            }
            this.dispatch("rollOutItem", a, b)
        }
    }, clickLabel: function (a, b) {
        this.textClickEnabled ? a.hidden || this.dispatch("clickLabel", a, b) : this.switchable && (!0 === a.hidden ? this.dispatch("showItem", a, b) : this.dispatch("hideItem", a, b))
    }, dispatch: function (a, b, c) {
        this.fire(a, {type: a, dataItem: b, target: this, event: c, chart: this.chart})
    }, createValue: function (a) {
        var b =
            this, c = b.fontSize;
        if (!1 !== a.visibleInLegend) {
            var d = b.maxLabelWidth;
            b.equalWidths || (b.valueAlign = "left");
            "left" == b.valueAlign && (d = a.legendEntry.getBBox().width);
            var e = d;
            if (b.valueText && 0 < b.valueWidth) {
                var f = b.color;
                b.useMarkerColorForValues && (f = a.color, a.legendKeyColor && (f = a.legendKeyColor()));
                !0 === a.hidden && (f = b.markerDisabledColor);
                var g = b.valueText, d = d + b.lx + b.markerLabelGap + b.valueWidth, h = "end";
                "left" == b.valueAlign && (d -= b.valueWidth, h = "start");
                f = AmCharts.text(b.container, g, f, b.chart.fontFamily,
                    c, h);
                f.translate(d, b.ly);
                b.entries[b.index].push(f);
                e += b.valueWidth + 2 * b.markerLabelGap;
                f.dItem = a;
                b.valueLabels.push(f)
            }
            b.index++;
            f = b.markerSize;
            f < c + 7 && (f = c + 7, AmCharts.VML && (f += 3));
            c = b.container.rect(b.markerSize, 0, e, f, 0, 0).attr({
                stroke: "none",
                fill: "#ffffff",
                "fill-opacity": .005
            });
            c.dItem = a;
            b.entries[b.index - 1].push(c);
            c.mouseover(function (c) {
                b.rollOverLabel(a, c)
            }).mouseout(function (c) {
                b.rollOutLabel(a, c)
            }).click(function (c) {
                b.clickLabel(a, c)
            })
        }
    }, createV: function () {
        var a = this.markerSize;
        return AmCharts.polygon(this.container,
            [a / 5, a / 2, a - a / 5, a / 2], [a / 3, a - a / 5, a / 5, a / 1.7], this.switchColor)
    }, createX: function () {
        var a = (this.markerSize - 4) / 2, b = {
            stroke: this.switchColor,
            "stroke-width": 3
        }, c = this.container, d = AmCharts.line(c, [-a, a], [-a, a]).attr(b), a = AmCharts.line(c, [-a, a], [a, -a]).attr(b);
        return this.container.set([d, a])
    }, createMarker: function (a, b, c, d, e, f, g, h) {
        var k = this.markerSize, l = this.container;
        e || (e = this.markerBorderColor);
        e || (e = b);
        isNaN(d) && (d = this.markerBorderThickness);
        isNaN(f) && (f = this.markerBorderAlpha);
        return AmCharts.bullet(l,
            a, k, b, c, d, e, f, k, g, h)
    }, validateNow: function () {
        this.invalidateSize()
    }, updateValues: function () {
        var a = this.valueLabels, b = this.chart, c, d = this.data;
        for (c = 0; c < a.length; c++) {
            var e = a[c], f = e.dItem, g = " ";
            if (d)f.value ? e.text(f.value) : e.text(""); else {
                if (void 0 !== f.type) {
                    var h = f.currentDataItem, k = this.periodValueText;
                    f.legendPeriodValueText && (k = f.legendPeriodValueText);
                    h ? (g = this.valueText, f.legendValueText && (g = f.legendValueText), g = b.formatString(g, h)) : k && (g = b.formatPeriodString(k, f))
                } else g = b.formatString(this.valueText,
                    f);
                if (k = this.valueFunction)h && (f = h), g = k(f, g);
                e.text(g)
            }
        }
    }, renderFix: function () {
        if (!AmCharts.VML) {
            var a = this.container;
            a && a.renderFix()
        }
    }, destroy: function () {
        this.div.innerHTML = "";
        AmCharts.remove(this.set)
    }
});
AmCharts.formatMilliseconds = function (a, b) {
    if (-1 != a.indexOf("fff")) {
        var c = b.getMilliseconds(), d = String(c);
        10 > c && (d = "00" + c);
        10 <= c && 100 > c && (d = "0" + c);
        a = a.replace(/fff/g, d)
    }
    return a
};
AmCharts.extractPeriod = function (a) {
    var b = AmCharts.stripNumbers(a), c = 1;
    b != a && (c = Number(a.slice(0, a.indexOf(b))));
    return {period: b, count: c}
};
AmCharts.newDate = function (a, b) {
    return date = "fff" == b ? AmCharts.useUTC ? new Date(a.getUTCFullYear(), a.getUTCMonth(), a.getUTCDate(), a.getUTCHours(), a.getUTCMinutes(), a.getUTCSeconds(), a.getUTCMilliseconds()) : new Date(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours(), a.getMinutes(), a.getSeconds(), a.getMilliseconds()) : new Date(a)
};
AmCharts.resetDateToMin = function (a, b, c, d) {
    void 0 === d && (d = 1);
    var e, f, g, h, k, l, m;
    AmCharts.useUTC ? (e = a.getUTCFullYear(), f = a.getUTCMonth(), g = a.getUTCDate(), h = a.getUTCHours(), k = a.getUTCMinutes(), l = a.getUTCSeconds(), m = a.getUTCMilliseconds(), a = a.getUTCDay()) : (e = a.getFullYear(), f = a.getMonth(), g = a.getDate(), h = a.getHours(), k = a.getMinutes(), l = a.getSeconds(), m = a.getMilliseconds(), a = a.getDay());
    switch (b) {
        case "YYYY":
            e = Math.floor(e / c) * c;
            f = 0;
            g = 1;
            m = l = k = h = 0;
            break;
        case "MM":
            f = Math.floor(f / c) * c;
            g = 1;
            m = l = k = h = 0;
            break;
        case "WW":
            0 ===
            a && 0 < d && (a = 7);
            g = g - a + d;
            m = l = k = h = 0;
            break;
        case "DD":
            m = l = k = h = 0;
            break;
        case "hh":
            h = Math.floor(h / c) * c;
            m = l = k = 0;
            break;
        case "mm":
            k = Math.floor(k / c) * c;
            m = l = 0;
            break;
        case "ss":
            l = Math.floor(l / c) * c;
            m = 0;
            break;
        case "fff":
            m = Math.floor(m / c) * c
    }
    AmCharts.useUTC ? (a = new Date, a.setUTCFullYear(e, f, g), a.setUTCHours(h, k, l, m)) : a = new Date(e, f, g, h, k, l, m);
    return a
};
AmCharts.getPeriodDuration = function (a, b) {
    void 0 === b && (b = 1);
    var c;
    switch (a) {
        case "YYYY":
            c = 316224E5;
            break;
        case "MM":
            c = 26784E5;
            break;
        case "WW":
            c = 6048E5;
            break;
        case "DD":
            c = 864E5;
            break;
        case "hh":
            c = 36E5;
            break;
        case "mm":
            c = 6E4;
            break;
        case "ss":
            c = 1E3;
            break;
        case "fff":
            c = 1
    }
    return c * b
};
AmCharts.intervals = {
    s: {nextInterval: "ss", contains: 1E3},
    ss: {nextInterval: "mm", contains: 60, count: 0},
    mm: {nextInterval: "hh", contains: 60, count: 1},
    hh: {nextInterval: "DD", contains: 24, count: 2},
    DD: {nextInterval: "", contains: Infinity, count: 3}
};
AmCharts.getMaxInterval = function (a, b) {
    var c = AmCharts.intervals;
    return a >= c[b].contains ? (a = Math.round(a / c[b].contains), b = c[b].nextInterval, AmCharts.getMaxInterval(a, b)) : "ss" == b ? c[b].nextInterval : b
};
AmCharts.dayNames = "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" ");
AmCharts.shortDayNames = "Sun Mon Tue Wed Thu Fri Sat".split(" ");
AmCharts.monthNames = "January February March April May June July August September October November December".split(" ");
AmCharts.shortMonthNames = "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" ");
AmCharts.getWeekNumber = function (a) {
    a = new Date(a);
    a.setHours(0, 0, 0);
    a.setDate(a.getDate() + 4 - (a.getDay() || 7));
    var b = new Date(a.getFullYear(), 0, 1);
    return Math.ceil(((a - b) / 864E5 + 1) / 7)
};
AmCharts.stringToDate = function (a, b) {
    var c = {}, d = [{pattern: "YYYY", period: "year"}, {pattern: "YY", period: "year"}, {
        pattern: "MM",
        period: "month"
    }, {pattern: "M", period: "month"}, {pattern: "DD", period: "date"}, {pattern: "D", period: "date"}, {
        pattern: "JJ",
        period: "hours"
    }, {pattern: "J", period: "hours"}, {pattern: "HH", period: "hours"}, {
        pattern: "H",
        period: "hours"
    }, {pattern: "KK", period: "hours"}, {pattern: "K", period: "hours"}, {
        pattern: "LL",
        period: "hours"
    }, {pattern: "L", period: "hours"}, {pattern: "NN", period: "minutes"}, {
        pattern: "N",
        period: "minutes"
    }, {pattern: "SS", period: "seconds"}, {pattern: "S", period: "seconds"}, {
        pattern: "QQQ",
        period: "milliseconds"
    }, {pattern: "QQ", period: "milliseconds"}, {pattern: "Q", period: "milliseconds"}], e = !0, f = b.indexOf("AA");
    -1 != f && (a.substr(f, 2), "pm" == a.toLowerCase && (e = !1));
    var f = b, g, h, k;
    for (k = 0; k < d.length; k++)h = d[k].period, c[h] = 0, "date" == h && (c[h] = 1);
    for (k = 0; k < d.length; k++)if (g = d[k].pattern, h = d[k].period, -1 != b.indexOf(g)) {
        var l = AmCharts.getFromDateString(g, a, f);
        b = b.replace(g, "");
        if ("KK" == g || "K" == g || "LL" ==
            g || "L" == g)e || (l += 12);
        c[h] = l
    }
    AmCharts.useUTC ? (d = new Date, d.setUTCFullYear(c.year, c.month, c.date), d.setUTCHours(c.hours, c.minutes, c.seconds, c.milliseconds)) : d = new Date(c.year, c.month, c.date, c.hours, c.minutes, c.seconds, c.milliseconds);
    return d
};
AmCharts.getFromDateString = function (a, b, c) {
    if (void 0 !== b)return c = c.indexOf(a), b = b.substr(c, a.length), "0" == b.charAt(0) && (b = b.substr(1, b.length - 1)), b = Number(b), isNaN(b) && (b = 0), -1 != a.indexOf("M") && b--, b
};
AmCharts.formatDate = function (a, b, c) {
    c || (c = AmCharts);
    var d, e, f, g, h, k, l, m = AmCharts.getWeekNumber(a);
    AmCharts.useUTC ? (d = a.getUTCFullYear(), e = a.getUTCMonth(), f = a.getUTCDate(), g = a.getUTCDay(), h = a.getUTCHours(), k = a.getUTCMinutes(), l = a.getUTCSeconds(), a = a.getUTCMilliseconds()) : (d = a.getFullYear(), e = a.getMonth(), f = a.getDate(), g = a.getDay(), h = a.getHours(), k = a.getMinutes(), l = a.getSeconds(), a = a.getMilliseconds());
    var n = String(d).substr(2, 2), p = e + 1;
    9 > e && (p = "0" + p);
    var q = "0" + g;
    b = b.replace(/W/g, m);
    m = h;
    24 == m && (m = 0);
    var r = m;
    10 > r && (r = "0" + r);
    b = b.replace(/JJ/g, r);
    b = b.replace(/J/g, m);
    r = h;
    0 === r && (r = 24, -1 != b.indexOf("H") && f--);
    m = f;
    10 > f && (m = "0" + f);
    var s = r;
    10 > s && (s = "0" + s);
    b = b.replace(/HH/g, s);
    b = b.replace(/H/g, r);
    r = h;
    11 < r && (r -= 12);
    s = r;
    10 > s && (s = "0" + s);
    b = b.replace(/KK/g, s);
    b = b.replace(/K/g, r);
    r = h;
    0 === r && (r = 12);
    12 < r && (r -= 12);
    s = r;
    10 > s && (s = "0" + s);
    b = b.replace(/LL/g, s);
    b = b.replace(/L/g, r);
    r = k;
    10 > r && (r = "0" + r);
    b = b.replace(/NN/g, r);
    b = b.replace(/N/g, k);
    k = l;
    10 > k && (k = "0" + k);
    b = b.replace(/SS/g, k);
    b = b.replace(/S/g, l);
    l = a;
    10 > l && (l = "00" +
    l);
    100 > l && (l = "0" + l);
    k = a;
    10 > k && (k = "00" + k);
    b = b.replace(/QQQ/g, l);
    b = b.replace(/QQ/g, k);
    b = b.replace(/Q/g, a);
    b = 12 > h ? b.replace(/A/g, "am") : b.replace(/A/g, "pm");
    b = b.replace(/YYYY/g, "@IIII@");
    b = b.replace(/YY/g, "@II@");
    b = b.replace(/MMMM/g, "@XXXX@");
    b = b.replace(/MMM/g, "@XXX@");
    b = b.replace(/MM/g, "@XX@");
    b = b.replace(/M/g, "@X@");
    b = b.replace(/DD/g, "@RR@");
    b = b.replace(/D/g, "@R@");
    b = b.replace(/EEEE/g, "@PPPP@");
    b = b.replace(/EEE/g, "@PPP@");
    b = b.replace(/EE/g, "@PP@");
    b = b.replace(/E/g, "@P@");
    b = b.replace(/@IIII@/g, d);
    b =
        b.replace(/@II@/g, n);
    b = b.replace(/@XXXX@/g, c.monthNames[e]);
    b = b.replace(/@XXX@/g, c.shortMonthNames[e]);
    b = b.replace(/@XX@/g, p);
    b = b.replace(/@X@/g, e + 1);
    b = b.replace(/@RR@/g, m);
    b = b.replace(/@R@/g, f);
    b = b.replace(/@PPPP@/g, c.dayNames[g]);
    b = b.replace(/@PPP@/g, c.shortDayNames[g]);
    b = b.replace(/@PP@/g, q);
    return b = b.replace(/@P@/g, g)
};
AmCharts.changeDate = function (a, b, c, d, e) {
    var f = -1;
    void 0 === d && (d = !0);
    void 0 === e && (e = !1);
    !0 === d && (f = 1);
    switch (b) {
        case "YYYY":
            a.setFullYear(a.getFullYear() + c * f);
            d || e || a.setDate(a.getDate() + 1);
            break;
        case "MM":
            b = a.getMonth();
            a.setMonth(a.getMonth() + c * f);
            a.getMonth() > b + c * f && a.setDate(a.getDate() - 1);
            d || e || a.setDate(a.getDate() + 1);
            break;
        case "DD":
            a.setDate(a.getDate() + c * f);
            break;
        case "WW":
            a.setDate(a.getDate() + c * f * 7);
            break;
        case "hh":
            a.setHours(a.getHours() + c * f);
            break;
        case "mm":
            a.setMinutes(a.getMinutes() +
            c * f);
            break;
        case "ss":
            a.setSeconds(a.getSeconds() + c * f);
            break;
        case "fff":
            a.setMilliseconds(a.getMilliseconds() + c * f)
    }
    return a
};