package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class ModalProdutoPage {

	private WebDriver driver;
	private By mensagemProdutoAdicionado = By.id("myModalLabel");
	private By descricaoProduto = By.className("product-name");
	private By precoProduto = By.cssSelector("div.modal-body  p.product-price");
	private By listaValoresSelecionados = By.cssSelector("div.divide-right .col-md-6:nth-child(2) span strong");
	private By subtotal = By.cssSelector(".cart-content p:nth-child(2) span.value");
	private By botaoProceedToCheckout = By.cssSelector("div.cart-content-btn a.btn-primary");

	public ModalProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	// flack teste: testes que não tem confiabilidade; adicione tempo de espera para
	// encontrar o elemento

	public String obterMensagemProdutoAdicionado() {
		// incluindo espera de 5 segundos; cada um segundo tenta de novo e se não
		// encontrar, lança a exceção.
		FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchFieldException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(mensagemProdutoAdicionado));

		driver.findElement(mensagemProdutoAdicionado).getText();
	}
	
	public String obterDescricaoProduto() {
		return driver.findElement(descricaoProduto).getText();	
	}
	
	public String obterPrecoProduto() {
		return driver.findElement(precoProduto).getText();
	}

	public String obterTamanhoProduto() {
		return driver.findElements(listaValoresSelecionados).get(0).getText();
	}

	public String obterCorProduto() {
		return driver.findElements(listaValoresSelecionados).get(1).getText();
	}

	public String obterQuantidadeProduto() {
		return driver.findElements(listaValoresSelecionados).get(2).getText();
	}
	
	public String obtersubTotal() {
		return driver.findElement(subtotal).getText();
	}
	
	public CarrinhoPage clicarbotaoProceedToCheckout() {
		driver.findElement(botaoProceedToCheckout).click();
		return new CarrinhoPage(driver);
	}
	
}
