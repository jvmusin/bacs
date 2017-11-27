package istu.bacs;

import istu.bacs.sybon.SybonCompiler;
import istu.bacs.sybon.SybonProblemCollection;
import istu.bacs.sybon.SybonApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
@SpringBootApplication
public class BacsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BacsApplication.class, args);
	}

	//todo: FOR TESTING PURPOSES ONLY
	@Bean
    public CommandLineRunner cmd(SybonApi sybon) {
        return (String... args) -> {
            if (true) return;
            SybonCompiler[] compilers = sybon.getCompilers();
            for (SybonCompiler compiler : compilers) {
                System.out.println(compiler);
            }
        };
    }
}