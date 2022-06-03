<!DOCTYPE html>
<html>
<head>
    <!-- Popperjs -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <!-- Tempus Dominus JavaScript -->
    <script src="https://cdn.jsdelivr.net/gh/Eonasdan/tempus-dominus@v6-alpha1.0.4/dist/js/tempus-dominus.js" crossorigin="anonymous"></script>

    <!-- Tempus Dominus Styles -->
    <link href="https://cdn.jsdelivr.net/gh/Eonasdan/tempus-dominus@v6-alpha1.0.4/dist/css/tempus-dominus.css" rel="stylesheet" crossorigin="anonymous">


</head>
<body>
<div class="container">
    <div class="row">
        <div class='input-group date' id='datetimepicker1'>
            <input type='text' class="form-control"/>
            <span class="input-group-addon\">
    <span class="glyphicon glyphicon-calendar"></span>
  </span>
        </div>

        <script>
            $(".date").datetimepicker({locale: "ru"});
        </script>
    </div>
</div>


</body>
</html>


