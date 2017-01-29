<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>定时任务添加</title>
		<link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
		<script type="text/javascript" src="lib/angular/angular.min.js"></script>
	</head>
	<body ng-app="schedual">
		<form class="form-horizontal container" name="taskForm" role="form" ng-controller="taskCtrl">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">任务名称</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="task_name" ng-model="task.taskName" ng-required="true" ng-trim="true"  ng-maxlength="10" ng-minlength="5" placeholder="任务名称"/>
		      <div ng-show="taskForm.task_name.$error.minlength" class="alert alert-danger" role="alert">
		      	<span>任务名称最少5位</span>
		      </div>
		      <div ng-show="taskForm.task_name.$error.maxlength" class="alert alert-danger" role="alert">
		      	<span>任务名称最大10位</span>
		      </div>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">任务分组</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="group_name" ng-model="task.groupName" ng-required="true" ng-trim="true" ng-minlength="5" ng-maxlength="20"  placeholder="任务所属分组">
		      <div ng-show="taskForm.group_name.$error.minlength" class="alert alert-danger" role="alert">
		      	<span>任务分组最少5位</span>
		      </div>
		      <div ng-show="taskForm.group_name.$error.maxlength" class="alert alert-danger" role="alert">
		      	<span>任务分组最大20位</span>
		      </div>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">应用名称</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="project_name" ng-model="task.projectName" ng-required="true" ng-trim="true" ng-minlength="3" ng-maxlength="50"  placeholder="任务所属应用">
		      <div ng-show="taskForm.project_name.$error.minlength" class="alert alert-danger" role="alert">
		      	<span>应用名称最少3位</span>
		      </div>
		      <div ng-show="taskForm.project_name.$error.maxlength" class="alert alert-danger" role="alert">
		      	<span>应用名称最大10位</span>
		      </div>
		    </div>
		  </div>
		  <div class="form-group">
		  	<label class="col-sm-2 control-label">任务地址</label>
		  	<div class="col-sm-10">
		  		<input type="url" class="form-control" name="task_url" ng-model="task.url" ng-required="true" ng-trim="true" placeholder="任务执行地址">
		  		<div ng-show="taskForm.task_url.$valid" class="alert alert-danger" role="alert">
		  			<span>任务地址必须以<b>http://</b>或<b>https://</b>开头</span>
		  		</div>
		  	</div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">CRON表达式</label>
		    <div class="col-sm-10" {{ taskForm.task_cron.$error.required ? 'has-error' : 'has-success'}}>
		      <input type="text" class="form-control" name="task_cron" ng-model="task.cron" ng-required="true" ng-trim="true" placeholder="定时任务CRON表达式">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">开始执行时间</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="start_time" ng-model="task.startTime"  ng-trim="true"  placeholder="任务开始执行时间">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">任务描叙</label>
		    <div class="col-sm-10">
		    	<textarea rows="2" cols="100" name="task_description" class="form-control" ng-model="task.description" ng-trim="true" ng-maxlength="200" placeholder="任务描叙"></textarea>
		    	<div ng-show="taskForm.description.$erro.maxlength" class="alert alert-danger" role="alert">
		    		<span>任务描叙最大长度为200位</span>
		    	</div>
		    </div>
		  </div>
		  <div class="form-group">
		  	<div class="col-sm-2"></div>
		  	<div class="col-sm-10">
		  		<button type="button" class="btn btn-primary" ng-click="add()">新增</button>
		  		<button type="button" class="btn btn-danger" ng-click="reset()">重置</button>
		  	</div>
		  </div>
		</form>
	</body>
	<script type="text/javascript" src="lib/jquery/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="app.js"></script>
	<script type="text/javascript" src="service/task.service.js"></script>
	<script type="text/javascript" src="controller/task.js"></script>
</html>