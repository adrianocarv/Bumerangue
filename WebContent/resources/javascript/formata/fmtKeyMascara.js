document.write("<script src='resources/javascript/formata/removeMask.js'></script>");

/* ---------------------------------------------------------------------------
	Descricao : Acrescenta m?scara enquanto o usu?rio digita a 
				informa??o.
	Parametros: 
				mascara - mascara.
				cpo - campo a ser formatado.
				prx - proximo campo.
				tclPres- evento
				m?scaras dispon?veis
					999.999.999-99
					99999-999
					9999.999.999-9
					999.999.999/9999-99
					99.999.999/9999-99
					99999.999999/9999-99
					9999.9999.999/9999-99
					999.999.999.999.999,99
					9,9999
					DD/MM
					DD/MM/YYYY
					DD/MM/YYYY HH:MM
					MM/YYYY
					HH:MM:SS
					HH:MM
					999/9999
					999-9999
					9999-9999
					(99)999-9999
					(99)9999-9999
					
				
	Retorno   : campo com a m?scara informada
	Globais   : -
	Utilizado no evento: onKeyDown, onKeyUp ou onKeyPres
*/
function fmtKeyMascara(mascara, cpo, prx, tclPres)
{
   somenteNumeros();
   var tcl = tclPres.keyCode;
   vr = cpo.value;
   vr = removeMask(vr);
   tam = vr.length;

   if (mascara == "999.999.999-99")
   {
      if ( tcl != 9 && tcl != 8 )
      {
       if (tam > 1 && tam <= 4)
        cpo.value =  vr.substr(0, tam -1) + '-' +
                     vr.substr(tam-1, 2);

       if ( tam > 4 && tam <= 7 )
         cpo.value = vr.substr(0, tam-4) + '.' +
                     vr.substr(tam-4, 3) + '-' +
                     vr.substr(tam-1, 2);

       if ( tam > 7 && tam < 11 )
         cpo.value = vr.substr(0, tam-7) + '.' +
                     vr.substr(tam-7, 3) + '.' +
                     vr.substr(tam-4, 3) + '-' +
                     vr.substr(tam-1, 2);
      }

      tamVr = vr.length;
      if (tamVr == 11)
         prx.focus();
   }
   else if (mascara == "99999-999")
   {
      if ( tcl != 9 && tcl != 8 )
      {
       if (tam > 1 && tam <= 6)
        cpo.value =  vr.substr(0, tam -1) + '-' +
                     vr.substr(tam-1, 2);
	  }

      tamVr = vr.length;
      if (tamVr == 11)
         prx.focus();
   }
   else if (mascara == "9999.999.999-9")
   {
      if ( tcl != 9 && tcl != 8 )
      {
       if (tam > 0 && tam <= 3)
        cpo.value =  vr.substr(0, tam) + '-';

       if ( tam > 3 && tam <= 6 )
         cpo.value = vr.substr(0, tam-3) + '.' +
                     vr.substr(tam-3, 3) + '-' +
                     vr.substr(tam, 1);

       if ( tam > 6 && tam < 11 )
         cpo.value = vr.substr(0, tam-6) + '.' +
                     vr.substr(tam-6, 3) + '.' +
                     vr.substr(tam-3, 3) + '-' +
                     vr.substr(tam, 1);
      }

      tamVr = vr.length;
      if (tamVr == 11)
         prx.focus();
   }
   else if (mascara == "9999.9999.999/9999-99")
   {
      if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 5 )
        cpo.value =  vr.substr(0, tam-1) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 5 && tam <= 8)
         cpo.value = vr.substr(0, tam-5) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 8 && tam <= 12 )
         cpo.value = vr.substr(0, tam-8) + '.' +
                     vr.substr(tam-8, 3) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 12 && tam < 17)
         cpo.value = vr.substr(0, tam-12) + '.' +
                     vr.substr(tam-12, 4) + '.' +
                     vr.substr(tam-8, 3)  + '/' +
                     vr.substr(tam-5, 4)  + '-' +
                     vr.substr(tam-1, 2);
      }
      tamVr = vr.length;
      if (tamVr == 17)
         prx.focus() ;
   }
   else if (mascara == "99.999.999/9999-99")
   {
   	  if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 5 )
        cpo.value =  vr.substr(0, tam-1) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 5 && tam <= 8)
         cpo.value = vr.substr(0, tam-5) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 8 && tam <= 11 )
         cpo.value = vr.substr(0, tam-8) + '.' +
                     vr.substr(tam-8, 3) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 12 && tam < 14)
         cpo.value = vr.substr(0, tam-11) + '.' +
                     vr.substr(tam-12, 3) + '.' +
                     vr.substr(tam-8, 3)  + '/' +
                     vr.substr(tam-5, 4)  + '-' +
                     vr.substr(tam-1, 2);
      }
      tamVr = vr.length;
      if (tamVr == 17)
         prx.focus() ;
   }
   else if (mascara == "99999.999999/9999-99")
   {
      if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 5 )
        cpo.value =  vr.substr(0, tam-1) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 5 && tam <= 11)
         cpo.value = vr.substr(0, tam-5) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 11 && tam < 17 )
         cpo.value = vr.substr(0, tam-11) + '.' +
                     vr.substr(tam-11, 6) + '/' +
                     vr.substr(tam-5, 4)  + '-' +
                     vr.substr(tam-1, 2);
      }
      tamVr = vr.length;
      if (tamVr == 17)
         prx.focus() ;
   }
   else if (mascara == "999.999.999.999.999,99")
   {
      
     tammax= cpo.maxLength;
     
     resto = tammax % 4;
     
     if(resto > 2)
     {
     		tammax = tammax - 4;
     }
     else
     {
     		tammax = tammax - Math.floor(tammax/4);
     		
     }
     
     if (tam < tammax && tcl != 8)
	  	{ 
	  		tam = vr.length + 1 ; 
	  	}
	  
	  	if (tcl == 8 )
	  	{
	  		tam = tam - 1 ; 
	  	}
	  		
	  	if ( tcl == 8 || tcl >= 48 && tcl <= 57 || tcl >= 96 && tcl <= 105 )
	  	{
	  		if ( tam <= 2 )
	  		{ 
	  	 		cpo.value = vr ; 
	  	 	}
	  	 	if ( (tam > 2) && (tam <= 5) )
	  	 	{
	  	 		cpo.value = vr.substr( 0, tam - 2 ) + ',' + 
	  	 									 vr.substr( tam - 2, tam ) ; 
	  	 	}
	  	 	if ( (tam >= 6) && (tam <= 8) )
	  	 	{
	  	 		cpo.value = vr.substr( 0, tam - 5 ) + '.' + 
	  	 									 vr.substr( tam - 5, 3 ) + ',' + 
	  	 									 vr.substr( tam - 2, tam ) ; 
	  	 	}
	  	 	if ( (tam >= 9) && (tam <= 11) )
	  	 	{
	  	 		cpo.value = vr.substr( 0, tam - 8 ) + '.' + 
	  	 									 vr.substr( tam - 8, 3 ) + '.' + 
	  	 									 vr.substr( tam - 5, 3 ) + ',' + 
	  	 									 vr.substr( tam - 2, tam ) ;
	  	 	}
	  	 	if ( (tam >= 12) && (tam <= 14) )
	  	 	{
	  	 		cpo.value = vr.substr( 0, tam - 11 ) + '.' + 
	  	 									 vr.substr( tam - 11, 3 ) + '.' + 
	  	 									 vr.substr( tam - 8, 3 ) + '.' + 
	  	 									 vr.substr( tam - 5, 3 ) + ',' + 
	  	 									 vr.substr( tam - 2, tam ) ; 
	  	 	}
	  	 	if ( (tam >= 15) && (tam <= 17) ){
	  	 		cpo.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;}
			}
			 
   }
    else if (mascara == "DD/MM")
   {
      if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 3)
         cpo.value = vr.substr(0, tam - 1) + '/' +
                     vr.substr(tam-1, 2);
      }
      tamVr = vr.length;
      if (tamVr == 4)
      {
         prx.focus() ;
      }
   }
    else if (mascara == "DD/MM/YYYY")
   {
      if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 3)
         cpo.value = vr.substr(0, tam - 1) + '/' +
                     vr.substr(tam-1, 2);
       if (tam > 3 && tam < 8)
         cpo.value = vr.substr(0, 2) + '/' +
                     vr.substr(2, 2) + '/' +
                     vr.substring(4);
      }
      tamVr = vr.length;
      if (tamVr == 8)
      {
         prx.focus() ;
      }
   }
    else if (mascara == "DD/MM/YYYY HH:MM")
   {
      if ( tcl != 8 && tcl != 9){
       if (tam > 1 && tam <= 3)
         cpo.value = vr.substr(0, tam - 1) + '/' +
                     vr.substr(tam-1, 2);
       if (tam > 3 && tam <= 7)
         cpo.value = vr.substr(0, 2) + '/' +
                     vr.substr(2, 2) + '/' +
                     vr.substring(4);
       if (tam > 7 && tam <= 9)
            cpo.value = vr.substr(0, 2) + '/' +
            			vr.substr(2, 2) + '/' +
            			vr.substr(4, 4) + ' ' +
                        vr.substring(8);
       if (tam > 9 && tam <= 11)
            cpo.value = vr.substr(0, 2) + '/' +
            			vr.substr(2, 2) + '/' +
            			vr.substr(4, 4) + ' ' +
            			vr.substr(8, 2) + ':' +
                        vr.substring(10);
      }

      tamVr = vr.length;
      if (tamVr == 12)
      {
         prx.focus() ;
      }
   }
   else if (mascara == "MM/YYYY")
   {
      if (tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam < 6)
         cpo.value = vr.substr(0, 2) + '/' +
                     vr.substring(2);
      }
      tamVr = vr.length;
      if (tamVr == 6)
         prx.focus() ;
   }
   else if (mascara == "9,9999")
   {
      if (tcl != 8 && tcl != 9)
      {
          if (tam > 0 && tam < 5)
             cpo.value = vr.substr(0, 1) + ',' + vr.substring(1);
      }

      tamVr = vr.length;

      if (tamVr == 5)  prx.focus() ;
   }
   else if (mascara == "HH:MM:SS")
   {
      if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 3)
         cpo.value = vr.substr(0, tam - 1) + ':' +
                     vr.substr(tam-1, 2);
       if (tam > 3 && tam < 8)
         cpo.value = vr.substr(0, 2) + ':' +
                     vr.substr(2, 2) + ':' +
                     vr.substring(4);
      }
      tamVr = vr.length;
      if (tamVr == 6)
         prx.focus() ;
   }
   else if (mascara == "HH:MM")
   {
      if ( tcl != 8 && tcl != 9)
      {
          if (tam > 1 && tam < 4)
            cpo.value = vr.substr(0, 2) + ':' +
                        vr.substring(2);

      }
      tamVr = vr.length;
      if (tamVr == 4)
         prx.focus() ;
   }


   else if (mascara == "999.999.999/9999-99")
   {
      if ( tcl != 8 && tcl != 9)
      {
       if (tam > 1 && tam <= 5 )
        cpo.value =  vr.substr(0, tam-1) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 5 && tam <= 8)
         cpo.value = vr.substr(0, tam-5) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 8 && tam <= 11 )
         cpo.value = vr.substr(0, tam-8) + '.' +
                     vr.substr(tam-8, 3) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
       if (tam > 11 && tam <= 13 )
         cpo.value = vr.substr(0, tam-11) + '.' +
                     vr.substr(tam-11, 3) + '.' +
                     vr.substr(tam-8, 3) + '/' +
                     vr.substr(tam-5, 4) + '-' +
                     vr.substr(tam-1, 2);
      }
      tamVr = vr.length;
      if (tamVr == 15)
         prx.focus() ;
   }
	else if (mascara == "999/9999")
	{
  	 		if (tcl != 8 && tcl != 9)
   		{
     			if (tam > 1 && tam < 8)
      	  			cpo.value = vr.substr(0, 3) + '/' + vr.substring(3);
        	}
        	tamVr = vr.length;
			if (tamVr == 7)
         prx.focus();

	}
	else if (mascara == "999-9999")
	{
  	 		if (tcl != 8 && tcl != 9)
   		{
     			if (tam > 1 && tam < 8)
      	  			cpo.value = vr.substr(0, 3) + '-' + vr.substring(3);
        	}
        	tamVr = vr.length;
			if (tamVr == 7)
         prx.focus();

	}
	else if (mascara == "9999-9999")
	{
   		if (tcl != 9)
     	 	{
       		if (tam > 1 && tam < 7)
      	  		cpo.value = vr.substr(0, 3) + '-' + vr.substring(3);
      	 	if(tam >= 7)
      	  		cpo.value = vr.substr(0, 4) + '-' + vr.substring(4);
         	
         }
         tamVr = vr.length;
			if (tamVr == 8)
         prx.focus();
    }
   else if (mascara == "(99)999-9999")
   {
       	if (tcl != 9)
     	 	{
       		if (tam > 1 && tam <= 7)
				  	cpo.value = vr.substr(0, 3) + '-' + vr.substring(3);
				if(tam > 7 && tam <=9)
      	  		cpo.value = '(' + vr.substr(0, 2) + ')' + vr.substr(2,3) + '-' + vr.substring(5);

         }
         tamVr = vr.length;
			if (tamVr == 9)
         prx.focus();
   
   }
   else if (mascara == "(99)9999-9999")
   {
       	if (tcl != 8 && tcl != 9)
     	 	{
       		if (tam > 1 && tam <= 8)
					cpo.value = vr.substr(0, 3) + '-' + vr.substring(3);
				if(tam > 8 && tam < 9)
      	  		cpo.value = '(' + vr.substr(0, 2) + ')' + vr.substr(2,3) + '-' + vr.substring(5);
				if (tam == 9)
         		cpo.value = '(' + vr.substr(0, 2) + ')' + vr.substr(2,4) + '-' + vr.substring(6);
         }
         tamVr = vr.length;
			if (tamVr == 10)
         prx.focus();
         
   }
   else
      alert("M?scara de entrada de dados n?o reconhecida");
}
