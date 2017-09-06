
<%@page import="entity.Utilisateur"%>
<%

    Utilisateur sessionUser_header = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);

%>

<br>
<!--  ======================================================================================-->
<!--  nav bar ================================================================-->
<!--  ======================================================================================-->
<nav class="navbar navbar-default">
    <div class="container-fluid noir_blanche">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed " data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">IsiTel</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="MyFiles">Fichier</a></li>
                <!---------------------------------------------->
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <span class="glyphicon glyphicon-link"></span>
                        Lien 
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">

                        <li>
                            <a target="_blank" href="https://accounts.google.com/ServiceLogin/signinchooser?service=cl&passive=1209600&osid=1&continue=https%3A%2F%2Fcalendar.google.com%2Fcalendar%2Frender%3Ftab%3Dmc%26pli%3D1%26t%3DAKUaPmaDQRhvfjA4sEZfyZq98tzlCFV1TFbnJ4ikSWyxZejaJn0SVNFn_lNR3PCirZVSWEPW6__DNBrE1Ltbsaq8aVYyqgfpsg%253D%253D&followup=https%3A%2F%2Fcalendar.google.com%2Fcalendar&scc=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin" class="">
                                Calendrier
                            </a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a target="_blank" href="https://www.taktikimmo.fr/" class="">www.taktikimmo.fr</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a target="_blank" href="https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1503669433&rver=6.7.6643.0&wp=MBI_SSL_SHARED&wreply=https:%2F%2Fmail.live.com%2Fdefault.aspx&lc=1036&id=64855&mkt=fr-FR&cbcxt=mai" class="">
                                Hotmail 
                            </a>
                        </li>   

                    </ul>
                </li>
                <!---------------------------------------------->
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="Message" class="">
                        <span class="glyphicon glyphicon-envelope"></span> Message
                        <b class="badge orange_noir" id="message_user">
                        </b>
                    </a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> <%=sessionUser_header.getPrenom() + " " + sessionUser_header.getNom()%><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li role="separator" class="divider"></li>
                            <%if (manager.ManagerUtilisateur.findUtilisateur(sessionUser_header.getId()) != null) {%>
                        <li><a href="ff"><img src="view/img_user/<%=sessionUser_header.getId()%>.jpg" class="img img-rounded img-responsive" width="30" height="30" ></a></li>
                                <%} else {%>
                        <li><a href="#"><img src="view/img_user/inconnu.jpg" class="img img-rounded img-responsive" width="30" height="30" ></a></li>
                                <%}%>
                        <li role="separator" class="divider"></li>
                        <li><a href="MyProfile"><span class="glyphicon glyphicon-user"></span> Mon compte</a></li>
                        <li><a href="Deconnexion" onclick="fermer()"><span class="glyphicon glyphicon-off"></span><b>Deconnexion</b></a></li>
                    </ul>
                </li>

            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!--  ======================================================================================-->
<!--  modal ajouter fichier ================================================================-->
<!--  ======================================================================================-->
<div class="modal fade" id="myModal1" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Ajouter un fichier</h4>
            </div>
            <div class="modal-body">
                <form action="Upload" method="post"  enctype="multipart/form-data">
                    <div class="form-group">
                        <div class="input-group">
                            <label class="input-group-btn">
                                <span class="btn btn-success">
                                    choisir&hellip; <input type="file" name="file" required style="display: none;" >
                                </span>
                            </label>
                            <input type="text" placeholder="Fichier" name="s" class="form-control left-rounded">
                            <div class="input-group-btn">
                                <button type="submit" class="btn  btn-inverse right-rounded "> Ajouter</button>
                            </div>
                        </div>
                    </div>
                </form>          
                <script>
                    $(function () {

                        // We can attach the `fileselect` event to all file inputs on the page
                        $(document).on('change', ':file', function () {
                            var input = $(this),
                                    numFiles = input.get(0).files ? input.get(0).files.length : 1,
                                    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                            input.trigger('fileselect', [numFiles, label]);
                        });

                        // We can watch for our custom `fileselect` event like this
                        $(document).ready(function () {
                            $(':file').on('fileselect', function (event, numFiles, label) {

                                var input = $(this).parents('.input-group').find(':text'),
                                        log = numFiles > 1 ? numFiles + ' files selected' : label;

                                if (input.length) {
                                    input.val(log);
                                } else {
                                    if (log)
                                        alert(log);
                                }

                            });
                        });

                    });
                </script>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>






<!--  ======================================================================================-->
<!--  modal ajouter utilisateur ================================================================-->
<!--  ======================================================================================-->
<div class="modal fade" id="myModal2" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Ajouter un utilisateur</h4>
            </div>
            <div class="modal-body">
                <form action="AddUser" method="post" class="form-horizontal">
                    <!---------------------------------------------------------->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input  name="name" type="text" class="form-control" id="inputEmail3" placeholder="Nom" required>
                        </div>
                    </div>
                    <!---------------------------------------------------------->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input  name="firstname" type="text" class="form-control" id="inputEmail3" placeholder="Prenom" required>
                        </div>
                    </div>

                    <!---------------------------------------------------------->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input  name="login" type="text" class="form-control" id="inputEmail3" placeholder="Login" required>
                        </div>
                    </div>
                    <!---------------------------------------------------------->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input  name="password" type="password" class="form-control" id="inputEmail3" placeholder="Mot de passe" required>
                        </div>
                    </div>
                    <!---------------------------------------------------------->
                    <div class="form-group">
                        <div class="btn-group col-sm-12">
                            <button type="submit" class="col-sm-6 btn btn-info"> Ajouter </button>
                            <button type="reset" class="col-sm-6 btn btn-danger"> Annuler </button>
                        </div>
                    </div>
                    <!---------------------------------------------------------->
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>

    </div>
</div>
<script>
    function fermer() {
        var objShell = new ActiveXObject("WScript.shell");
        objShell.run('c:\\fermer.bat');
    }
</script>
