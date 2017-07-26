<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
</head>

<body>
<select name="jumpMenu" id="jumpMenu" onChange="jumpMenu('parent',this,0)">
    <option id="1" value="跳转URL">111</option> // 111 是显示给用户的信息
    <option id="2" value="跳转URL">222</option>
    <option id="3" value="跳转URL">333</option>
    <option id="4" value="跳转URL">444</option>
    <option id="5" value="跳转URL">555</option>
</select>
<script type="text/javascript">
    function display(optionID){
        var all_options = document.getElementById("jumpMenu").options;

        for (var i=0; i<all_options.length; i++){
            if (all_options[i].id == optionID) // 根据option标签的ID来进行判断 测试的代码这里是两个等号
            {
                all_options[i].selected = true;
            }
        }
    };
    display("4");
</script>
</body>
</html>

