package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class ModalProdutoPage {

	private WebDriver driver;
	private By mensagemProdutoAdicionado = By.id("myModalLabel");

	public ModalProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	// flack teste: testes que não tem confiabilidade; adicione tempo de espera para encontrar o elemento
	
	public String obterMensagemProdutoAdicionado() {
		// incluindo espera de 5 segundos; cada um segundo tenta de novo e se não
		// encontrar, lança a exceção.
		FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchFieldException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(mensagemProdutoAdicionado));

		driver.findElement(mensagemProdutoAdicionado).getText();
	}
}
