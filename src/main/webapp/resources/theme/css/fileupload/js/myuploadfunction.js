$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        
        done: function (e, data) {
        	//$("tr:has(td)").remove();
            $.each(data.result, function (index, file) {
    			if(file.fileName.length > 0){
                	$('#success_user_Update_Message').show();
    				$('#success_user_Update_Message').html(file.fileName);
    				setTimeout(function() {
    					 $('#success_user_Update_Message').fadeOut('slow');												   
    					 }, 8000);
    			} else{
    				 $('#error_user_Update_Message').show();    				 
	   				 $('#error_user_Update_Message').html(file.fileType);
	   				 setTimeout(function() {
	   				 $('#error_user_Update_Message').fadeOut('slow');												   
	   				 }, 8000);
    			}
    			

            	/*
                $("#uploaded-files").append(
                		$('<tr/>')
                		.append($('<td/>').text(file.fileName))
                		.append($('<td/>').text(file.fileSize))
                		.append($('<td/>').text(file.fileType))
                		.append($('<td/>').html("<a href='rest/controller/get/"+index+"'>Click</a>"))
                		)//end $("#uploaded-files").append()
                		*/
            }); 
        },
        
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
   		},
   		
		dropZone: $('#dropzone'),
		
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('#error_user_Update_Message').show();
			$('#error_user_Update_Message').html(
					'Sorry! file is not uploaded. Your session is timed-out' 
					+ ' or something wrong happend on server side.'
					+ ' <strong>Please login again to proceed.</strong>');
			setTimeout(function() {
				   $('#error_user_Update_Message').fadeOut('slow');												   
				}, 8000);

	    }
    });
});