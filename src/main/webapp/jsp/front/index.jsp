<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>光小微网</title>
    <style type="text/css">
        .spinner {
            width: 200px;
            height: 200px;
            position: relative;
            top: 500px;
            margin: 100px auto;
        }

        .double-bounce1,
        .double-bounce2 {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            background-color: #3ABCFF;
            opacity: 0.6;
            position: absolute;
            top: 0;
            left: 0;
            -webkit-animation: bounce 2.0s infinite ease-in-out;
            animation: bounce 2.0s infinite ease-in-out;
        }

        .double-bounce2 {
            -webkit-animation-delay: -1.0s;
            animation-delay: -1.0s;
        }

        @-webkit-keyframes bounce {
            0%,
            100% {
                -webkit-transform: scale(0.0)
            }
            50% {
                -webkit-transform: scale(1.0)
            }
        }

        @keyframes bounce {
            0%,
            100% {
                transform: scale(0.0);
                -webkit-transform: scale(0.0);
            }
            50% {
                transform: scale(1.0);
                -webkit-transform: scale(1.0);
            }
        }
    </style>
</head>

<body>
    <div class="loading" style="display:block; width:100%; height:100%; position:fixed; left:0px; top:0px; z-index:9999; background-color: #fff">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <div class="swiper-container">
        <div class="swiper-wrapper">
            <div class="swiper-slide" style="background-image:url(//${storageDomain}/storage/gmxx/images/home_bg01.jpg)">
            </div>
            <div class="swiper-slide" style="background-image:url(//${storageDomain}/storage/gmxx/images/home_bg02.jpg)">
            </div>
            <div class="swiper-slide" style="background-image:url(//${storageDomain}/storage/gmxx/images/home_bg03.jpg)">
            </div>
        </div>
        <div class="swiper-pagination"></div>
    </div>
    <div class="circle">
        <div class="ring">
            <a href="<%=basePath%>front/overview.htm" class="menuItem fa fa-home fa-2x"><span class="menu-title">校园概况</span></a>
            <a href="<%=basePath%>front/blog/list_v2.htm?blogType=校园新闻" class="menuItem fa fa-comment fa-2x"><span class="menu-title">新闻中心</span></a>
            <a href="<%=basePath%>front/scenery.htm" class="menuItem fa fa-camera fa-2x"><span class="menu-title">校园掠影</span></a>
            <a href="http://gmxx.goldskyer.com/h5/gmxx-teacher.html" class="menuItem fa fa-user fa-2x"><span class="menu-title">名师风采</span></a>
            <a href="#" class="menuItem fa fa-group fa-2x"><span class="menu-title">班级社区</span></a>
            <a href="<%=basePath%>front/blog/list_image.htm?blogType=学生获奖" class="menuItem fa fa-trophy fa-2x"><span class="menu-title">教育成果</span></a>
        </div>
        <!-- <a href="#" class="center fa fa-th fa-2x"> -->
        <a href="#" class="center">    
            <img class="logo_img" src="//${storageDomain}/storage/gmxx/images/logo.png">
        </a>
    </div>
    <script src="../dist/common.js"></script>
    <script src="../dist/index.js"></script>
</body>

</html>
