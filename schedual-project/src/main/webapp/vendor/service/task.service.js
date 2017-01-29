/**
 * 周期任务
 */
schedual.factory("taskService", function($http, $q, $log) {
	return {
		get : function(taskId) {
			$http.get('./period/get/' + taskId).then(function(response) {
				return response.data;
			}, function(error) {
				$log.error('周期任务(' + taskId + ')信息获取失败！');
				return $q.reject(error);
			});
		},
		add : function(task) {
			$http.post('./period/add',task).then(function(response){
				return response.data;
			},function(error){
				$log.error('周期任务信息添加失败！');
				$q.reject(error);
			});
		},
		del : function(taskId) {
			$http.delete('./period/del/'+taskId).then(function(response){
				return response.data;
			},function(error){
				$log.error('周期任务('+taskId+')信息删除失败！');
				return $q.reject(error);
			});
		},
		pause : function(taskId){
			$http.put('./period/pause/'+taskId).then(function(response){
				return response.data;
			},function(error){
				$log.error('周期任务('+taskId+')暂停失败！');
				return $q.reject(error);
			});
		},
		recove:function(taskId){
			$http.put('./period/recove/'+taskId).then(function(response){
				return response.data;
			},function(error){
				$log.error('周期任务('+taskId+')恢复失败！');
				return $q.reject(error);
			});
		},
		findList : function(task, page, size) {
			$http.get('./period/'+page+'/'+size)
		},
		notify:function(msg){
			alert(msg);
		}
	}
});