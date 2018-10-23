
<!DOCTYPE html>
<html>
    <head>
        <%@include file="import_head.jspf"%>
        <title> <fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_titulo"/>
        </title>
    </head>
    <body style="font-family: 'Roboto Slab', Helvetica">
        <%@include file="import_navbar.jspf"%>
        <div class="container"> 
            <c:if test="${ not empty msg }">
                <div class="alert alert-warning" role="alert">
                    ${ msg }
                </div>
            </c:if>

            <p class="tituloForm">
            <h5 class="offset-5"><fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_titulo"/></h5>	
            
            <form  method="Post" action = "UsuarioValidaServlet"> 
                <fieldset class="form-group ">
                    
                    <div class="form-group col-md-6"> 
                        <label for="nomeUsuario"><br><fmt:message key="br.cefetrj.sisgee.resources.form.nome"/></label>
                        <input type="text" class="form-control ${ not empty nomeUsuarioMsg ? 'is-invalid': 'is-valid' } " id="nomeUsuario" name="nomeUsuario" value="${ param.nomeUsuario}">
                        <c:if test="${ not empty nomeUsuarioMsg }">
                            <div class="invalid-feedback">${ nomeUsuarioMsg }</div>
                        </c:if>
                    </div>
                    
                    <div class="form-group col-md-6"> 
                        <label for="loginUsuario">Login</label>
                        <input type="text" class="form-control ${ not empty loginUsuarioMsg ? 'is-invalid': 'is-valid' }" id="loginUsuario" name="loginUsuario" value="${ param.loginUsuario}">
                        <c:if test="${ not empty loginUsuarioMsg }">
                            <div class="invalid-feedback">${ loginUsuarioMsg }</div>
                        </c:if>
                    </div>
                    
                    <div class="form-group col-md-6"> 
                        <label for="senhaUsuario"><fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_senha"/></label>
                        <input type="password" class="form-control ${ not empty senhaUsuarioMsg ? 'is-invalid': 'is-valid' } " id="senhaUsuario" name="senhaUsuario" value="${ param.senhaUsuario}">
                        <c:if test="${ not empty senhaUsuarioMsg }">
                            <div class="invalid-feedback">${ senhaUsuarioMsg }</div>
                        </c:if>
                    </div>
                    
                    <div class="form-group col-md-6"> 
                        <label for="perfilUsuario"><fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_perfil"/> </label>
                        <select name = "perfilUsuario" id="perfilUsuario" class="form-control is-valid">
                                <option value="semAcesso" ${ perfilUsuario eq "semAcesso" ? "selected" : ""} ><fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_semAcesso"/> </option>
                                <option value="Usuario" ${ perfilUsuario eq "Usuario" ? "selected" : ""}><fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_usuario"/> </option>
                                <option value="Administrador" ${ perfilUsuario eq "Administrador" ? "selected" : ""} ><fmt:message key = "br.cefetrj.sisgee.cadastrar_usuario.msg_administrador"/> </option>			
                        </select>
                    </div>
                </fieldset>
                
                <button type="submit" class="btn btn-primary" ><i class="far fa-save"></i> <fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_salvar"/></button>
                <button type="button" class="btn btn-secondary" onclick="javascript:location.href = 'index.jsp'"><i class="far fa-times-circle"></i> <fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cancelar"/></button>

            </form>
        </div>
        <%@include file="import_footer.jspf"%>
        <%@include file="import_finalbodyscripts.jspf"%>
        <%@include file="import_scripts.jspf"%>
    </body>
    
</html>
