 
<%@page import="entity.Utilisateur"%>
<%@page import="java.util.List"%>
<div class="container ">

    <br> <br> <br> <br>


    <!--  ======================================================================================-->
    <!--  ROW ACCEUIL ================================================================-->
    <!--  ======================================================================================-->
    <div class="row">
        <div class="col-lg-offset-3 col-xs-12" >
            <a href="#" class='glyphicon glyphicon-off btn btn-danger btn-lg' onclick="fermer()" ></a>

            <script>
                function fermer() {
                    var objShell = new ActiveXObject("WScript.shell");
                    objShell.run('c:\\fermer.bat');
                }
            </script>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-lg-offset-3 col-lg-6 col-xs-12" >
            <div class="panel panel-collapse panel-primary" >
                <div class="panel-heading panel-title orange_noir "><h4>Connexion</h4></div>
                <div class="panel-body blanche_noir" >
                    <br>
                    <form class="form-horizontal" action="TableauDeBord" method="post">
                        <!----------------------------------------->
                        <div class="col-xs-12 ">
                            <div class="input-group ">
                                <span class="input-group-addon input-lg"><i class=" glyphicon glyphicon-user"></i></span>
                                <input type="text" class="form-control input-lg" placeholder="Login"  type="text" name="login" required>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon input-lg"><i class="glyphicon glyphicon-lock"></i></span>
                                <input type="password" class="form-control input-lg" placeholder="Mot de passe"  type="password" name="pwd" required>
                            </div>
                            <br>
                            <div class="form-group ">

                                <div class="btn-group col-xs-12">
                                    <button type="submit" class="col-xs-6 btn btn-lg btn-info  "> Se connecter </button>
                                    <button type="reset" class="col-xs-6 btn btn-lg   btn-default"> Annuler </button>
                                </div>
                            </div>
                        </div>

                    </form>
                    <br> <br> <br> <br>

                </div>

            </div>
        </div>
    </div>
</div>
