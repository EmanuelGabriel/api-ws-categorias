
    function criarCategoria(){

     var formData = {
           codigo: $("#codigo").val(),
           nome: $("#nome").val(),
           descricao: $("#descricao").val(),
         };

       console.log(formData);

       $.ajax({
         method: "POST",
         url: "/api/v1/categorias",
         data: JSON.stringify(formData),
         dataType: "JSON",
         contentType: "application/json; charset=utf-8",
         success: function (resp) {

            $("#codigo").val(resp.codigo);
            alert("Categoria '" + resp.nome + "' registrada com sucesso!");
         }


      }).fail(function (xhr, status, errorThrown) {

          console.log(xhr);
          console.log(xhr.responseJSON.mensagem);
      	  console.log(xhr.responseJSON.status);

      	  if(xhr.responseJSON.status === 400){
      	    if(xhr.responseJSON.titulo === "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."){
      	       return alert(xhr.responseJSON.titulo);
      	     }

      	  }

      	         var msg = "Atenção!\n" + "mensagem: " + xhr.responseJSON.mensagem;
      	         alert(msg);
      		});

       console.log(codigo);


}


    function pesquisarCategoriaPorNome(){

    var nomeCategoria = $("#nome-busca").val();
    console.log(nomeCategoria);

    $('#tabelaCategorias > tbody > tr').remove();

    	$.ajax({
    			method: "GET",
    			url: "/api/v1/categorias/buscar-por-nome",
    			data: "nomeCategoria=" + nomeCategoria,
    			contentType: "application/json; charset=utf-8",
    			success: function (resp) {

    				console.log(resp);

    		if(resp.length != 0){

    				for(var i = 0; i < resp.length; i++){

    					var dataCadastro = resp[i].dataCadastro;
    					data = new Date(dataCadastro);
    					dataFormatada = data.toLocaleDateString('pt-BR', {timeZone: 'UTC'});

    					$('#tabelaCategorias > tbody')
    				    .append('<tr> <td> ' + resp[i].codigo + ' </td>  <td width="30%"> ' + resp[i].nome + ' </td>  <td width="50%"> ' + resp[i].descricao + ' </td>   <td width="20%"> ' + dataFormatada + ' </td> <td width="50%"> <button type="button" class="btn btn-primary" onclick="buscarCategoriaPorId(' + resp[i].codigo + ')">Ver</button>  </td>   </tr>');

    				}


    		}else {

    		   alert("Nenhuma categoria encontrada com o nome '" + nomeCategoria + "'");

    			}

    		}

    		}).fail(function (xhr, status, errorThrown) {
          		 console.log(xhr);
    			 alert("Erro ao buscar categorias - " + xhr.responseText);
    		});

}




    function atualizarPorIdCategoria(id){

 	  	var id = $("#codigo").val();
		var nome = $("#nome").val();
		var descricao = $("#descricao").val();

			$.ajax({
			method: "PUT",
			url: "/api/v1/categorias/" + id,
			data: JSON.stringify({id: codigo, nome: nome, descricao: descricao}),
			contentType: "application/json; charset=utf-8",
			success: function (resp) {

				alert("Categoria atualizada com sucesso!");

				$("#modalPesquisarCategorias").modal('hide');

			}
		}).fail(function (xhr, status, errorThrown) {
      		 console.log(xhr);
			 alert("Erro ao tentar atualizar a categoria - " + xhr.responseJSON.mensagem);
		});


 	}



	function buscarCategoriaPorId(id){

			$.ajax({
			method: "GET",
			url: "/api/v1/categorias/" + id,
			contentType: "application/json; charset=utf-8",
			success: function (resp) {

				$("#codigo").val(resp.codigo);
				$("#nome").val(resp.nome);
				$("#descricao").val(resp.descricao);

				$("#btn-salvar").prop("disabled", true);
				$("#btn-editar").show();
				$("#btn-editar").prop("disabled", false);


				$("#modalPesquisarCategorias").modal('hide');

			}
		}).fail(function (xhr, status, errorThrown) {
      		 console.log(xhr);
			 alert("Erro ao buscar categoria - " + xhr.responseJSON.mensagem);
		});

 	}