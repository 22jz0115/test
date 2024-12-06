<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>タスク履歴</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script>
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', () => {
                navigator.serviceWorker.register('/test/service-worker.js').then((registration) => {
                    console.log('Service Worker registered with scope:', registration.scope);
                }).catch((error) => {
                    console.error('Service Worker registration failed:', error);
                });
            });
        }
    </script>
</head>
<body>
    <header>
        <a href="Category?from=TaskHistory"><img src="assets/img/Vector.png" alt=""></a>
        <h1>タスク履歴</h1>
    </header>
     
    <h2 class="taskcategory">${categoryName.categoryName}</h2>
    <div>
        <ul class="taskhistory">
          <c:forEach var="task" items="${taskList}">
            <li class="history">
               <!-- リンクにfrom=TaskHistoryを追加 -->
               <a href="TaskDetail?taskId=${task.id}&from=TaskHistory&categoryId=${task.categoryId}">
                    <p class="taskname">${task.taskName}</p>
                </a>
            </li>
          </c:forEach>
        </ul>
    </div>
</body>
</html>
