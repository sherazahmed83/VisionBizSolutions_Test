//	WebHelp 5.10.001
var S=new Array();
var A=new Array();
var gaFtsStop=new Array();
var gaFtsStem=new Array();
var gbWhLang=false;

/*% OutputSortOrder.js("S[%s]=%d;\r\n"); %*/
/*% OutputFtsStems.js("gaFtsStem[%d] = \"%s\";\r\n"); %*/

var gsBiggestChar="䶮";
function getBiggestChar()
{
	return gsBiggestChar;
}

function getCharCode(str,i)
{
	var code=str.charCodeAt(i);
	if ((typeof(S[code]) != 'undefined')&&(S[code] != null))
	{
		return S[code] ;
	}
	return code;
}

function getAccentCharOrder(str, i)
{
	var code=str.charCodeAt(i);
	if ((typeof(A[code]) != 'undefined')&&(A[code] != null))
	{
		return A[code] ;
	}
	else if ((typeof(S[code]) != 'undefined')&&(S[code] != null))
	{
		return S[code] ;
	}
	return code;	
}

function compare(strText1,strText2)
{
	for(var i=0;i<strText1.length && i<strText2.length;i++)
	{
		var charCode1 = getCharCode(strText1,i);
		var charCode2 = getCharCode(strText2,i);
			
		if(charCode1<charCode2) return -1;
		if(charCode1>charCode2) return 1;
	}
	if(strText1.length<strText2.length) return -1;
	if(strText1.length>strText2.length) return 1;
	
	//compare accent
	/*for(var i=0;i<strText1.length ;i++)
	{
		var charCode1 = getAccentCharOrder(strText1,i);
		var charCode2 = getAccentCharOrder(strText2,i);
			
		if(charCode1<charCode2) return -1;
		if(charCode1>charCode2) return 1;
	}*/	
	return 0;	
}

gbWhLang=true;