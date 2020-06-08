package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

public class HomePageTest extends BaseTests {

	@Test
	// validando quantidade de produtos na pagina inicial
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8)); // validando que tem 8 itens na pagina inicial
	}

	@Test
	// validando se no carrinho tem 0 produtos
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0)); // validando quantos produtos tem no carrinho
	}

	ProdutoPage produtoPage;

	@Test
	// validando se os detalhes do itens são iguais ao que consta na pagina inicial
	String nomeProduto_ProdutoPage;

	public void testValidarDetalhesDoItem_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		System.out.print(nomeProduto_HomePage);
		System.out.print(precoProduto_HomePage);

		// quando clicar no produto, devemos ir para a pagina de produto e não ficar na
		// home
		// criando um objeto para a pagina de produto
		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		System.out.print(nomeProduto_ProdutoPage);
		System.out.print(precoProduto_ProdutoPage);

		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_HomePage));

	}

	LoginPage loginPage;

	@Test
	public void loginSucesso_UsuarioLogado() {
		// clicando no botão Sign In na home
		loginPage = homePage.clicarBotaoSingIn(); // instanciando váriavel loginPage para retornar para o login

		// informando o usuário e senha
		loginPage.preencherEmail("natalia@test.com");
		loginPage.preencherPassword("12345");

		// clicando no botão Sign In para logar
		loginPage.clicarBotaoSignIn();

		// validando se o usuário está de fato logado
		assertThat(homePage.estaLogado("Natalia Alves"), is(true));
		carregarPaginaInicial();

	}

	ModalProdutoPage modalProdutoPage;

	@Test
	public void incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {
		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;

		// Pré-condição: validando se o usuário está logado; se nao estiver, chama o
		// metódo para logar.
		if (!homePage.estaLogado("Natalia Alves")) {
			loginSucesso_UsuarioLogado();
		}
		// selecionando o produto
		testValidarDetalhesDoItem_DescricaoEValorIguais();

		// selencionando o tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());

		produtoPage.selecionarOpcaoDropdown(tamanhoProduto);
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());

		// selecionando cor
		produtoPage.selecionarCorPreta();

		// selecionando quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// adicionando produto no carrinho
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validações
		// verificando se a mensagem termina assim:
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));

		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));

		// armazenando a variavel como ela tá
		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		// removendo o $ da variavel
		precoProdutoString = precoProdutoString.replace("$", "");
		// criando a variavel numerica; pegando a variavel string e convertendo ela em
		// double
		Double precoProduto = Double.parseDouble(precoProdutoString);

		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));

		String subtotalProdutoString = modalProdutoPage.obtersubTotal();
		subtotalProdutoString = subtotalProdutoString.replace("$", "");
		Double subtotal = Double.parseDouble(subtotalProdutoString);

		Double subTotalCalculado = quantidadeProduto * precoProduto;

		assertThat(subtotal, is(subTotalCalculado));

	}

	@Test
	public void irParaCarrinho_InformacoesPersistidas() {
		// Pré-condiçao
		incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		CarrinhoPage carrinhoPage = modalProdutoPage.clicarbotaoProceedToCheckout();

	}

}
