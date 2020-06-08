package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	private WebDriver driver;

	List<WebElement> listaProdutos = new ArrayList();

	private By textoProdutosNoCarrinho = By.className("cart-products-count");
	private By produtos = By.className("product-description"); // pegando a lista de produtos do carrinho
	private By descricoesDosProdutos = By.cssSelector(".product-description a"); // pegando a descri��o de um item em especifico
	private By precosDosProdutos = By.className("price"); // pegando o pre�o de um produto especifico
	private By botaoSignIn = By.cssSelector("#_desktop_user_info span.hidden-sm-down"); //bot�o Sign in
	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down"); //pegando usu�rio logado
	


	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public int contarProdutos() {
		carregarListaProdutos();
		return listaProdutos.size();
	}

	// referenciando a lista de produtos
	private void carregarListaProdutos() {
		listaProdutos = driver.findElements(produtos);

	}

	public int obterQuantidadeProdutosNoCarrinho() {
		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosNoCarrinho).getText(); // obtendo retorno
																										// de uma tag

		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", ""); // alterando o caracter por outro
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");

		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho); // convertendo string em inteiro

		return qtdProdutosNoCarrinho;
	}

	public String obterNomeProduto(int indice) { // CUIDADO: O FINDELEMENT TEM QUE SER DO TIPO LIST, SEN�O O GET N�O
													// ACHA O INDICE.
		return driver.findElements(descricoesDosProdutos).get(indice).getText();

	}

	public String obterPrecoProduto(int indice) {
		return driver.findElements(precosDosProdutos).get(indice).getText();

	}
	
	public ProdutoPage clicarProduto (int indice) {
		driver.findElements(descricoesDosProdutos).get(indice).click(); // dando clique no produto 0
		return new ProdutoPage(driver);
		
	}
	
	public LoginPage clicarBotaoSingIn() {
		driver.findElement(botaoSignIn).click();
		return new LoginPage(driver); // retornando para a pagina de login
	}
	
	
	public boolean estaLogado(String texto) {
		return texto.equals(driver.findElement(usuarioLogado).getText());
	}
	
}
