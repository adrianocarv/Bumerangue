	/* -----------------------------------------------------------------------------
Descricao : Remove mascara de uma string
Parametro : str - string
Retorno   :
Globais   : -
Data de cria??o: 22/11/2000
Autor: Jos? Bernardo Filho
*/
function removeMask(str)
{
   while(str.indexOf(".") != -1)
      str = str.replace(".", "");
   while(str.indexOf("/") != -1)
      str = str.replace( "/", "" );
   while(str.indexOf("-") != -1)
      str = str.replace( "-", "" );
   while(str.indexOf(",") != -1)
      str = str.replace( ",", "" );
   while(str.indexOf(":") != -1)
      str = str.replace( ":", "" );
   while(str.indexOf(" ") != -1)
      str = str.replace( " ", "" );
   return str;
}

function somenteNumeros()
{
  var validos  = "0123456789"
  var codTecla = window.event.keyCode;
  var tecla    = String.fromCharCode(codTecla);
  if (validos.indexOf(tecla) < 0 && codTecla != 13)
  {
    window.event.keyCode = 0;
  }
}
function somenteNaoNumeros()
{
  var invalidos  = "0123456789"
  var codTecla = window.event.keyCode;
  var tecla    = String.fromCharCode(codTecla);
  if (invalidos.indexOf(tecla) > 0)
  {
    window.event.keyCode = 0;
  }
}


