var activities = [];

function getAllActivities(){
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/api/activities',
		dataType: 'json', // data type of response
		success: function (data) {
			activities = data;
			render(data);
		}
	});
}

function getUser() {
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/api/users/3',
		dataType: 'json', // data type of response
		success: function (data) {
			console.log(data);
		}
	});
}

function render(data){
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	if(list.length > 0) {
		$.each(list, function (index, i) {
			var tr = $('<tr></tr>');
			var name = i.name;

			tr.append('<td>' + name + '</td>' +
				'<td>' + '<input type="button" value="More details" onclick="moreDetails(\'' + index + '\')" />');

			i++;
			$('#activities').append(tr);
		});
	}else{
		alert("Currently there is no activities!");
	}
}

function moreDetails(index) {
	var activity = activities[index];
	var name = activity != null ? activity.name: " bla";
	alert("More details about " + name);
}