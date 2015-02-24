package com.avro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.avro.entity.Author;
import com.avro.entity.Book;
import com.avro.entity.Publisher;

public class AvroExample {

	public static void main(final String[] args) throws IOException {
		List<Book> bookList = createBooks();

		// Create a temp file
		File store = File.createTempFile("book", ".avro");

		serialize(bookList, store);

		deserialize(store);
	}

	/**
	 * Deserialize 
	 * 
	 * @param store - 
	 * @throws IOException - Caso ocorra algo inesperado
	 */
	private static void deserialize(File store) throws IOException {
		DatumReader<Book> bookDatumReader = new SpecificDatumReader<Book>(Book.class);
		DataFileReader<Book> bookFileReader = new DataFileReader<Book>(store, bookDatumReader);
		while (bookFileReader.hasNext()) {
			Book b1 = bookFileReader.next();
			System.out.println("deserialized from file: " + b1);
		}
	}

	/**
	 * Serialize a list of {@link Book}
	 * 
	 * @param bookList - lista de livros a serem serializados
	 * @param store
	 * @throws IOException
	 */
	private static void serialize(List<Book> bookList, File store)
			throws IOException {
		System.out.println("serializing books to temp file: " + store.getPath());

		DatumWriter<Book> bookDatumWriter = new SpecificDatumWriter<Book>(Book.class);
		DataFileWriter<Book> bookFileWriter = new DataFileWriter<Book>(bookDatumWriter);
		bookFileWriter.create(bookList.get(0).getSchema(), store);

		for (Book book : bookList) {
			bookFileWriter.append(book);
		}

		bookFileWriter.close();
	}

	/**
	 * Create array of books to be used
	 * 
	 * @return
	 */
	private static List<Book> createBooks() {
		List<Book> bookList = new ArrayList<Book>();
		
		bookList.add(createBook("Java Para Iniciantes", "Crie, Compile e Execute Programas Java Rapidamente", 9788565837835L, 632, 5, "Brochura", 2013, "9788565837835", "Informatica-Programaçao", "Português", "Bookman", null));
		bookList.add(createBook("Use a Cabeça! Java", null, 9788576081739L, 496, 2, "Brochura", 2007, "9788576081739", "Informatica", "Português", "Alta Books", null));
		bookList.add(createBook("Programação de Computadores em Java", null, 9788561893361L, 1456, 2, "Capa Dura", 2014, "9788561893361", "Informática - Programação", "Português", "Nova Terra", "Rui Rosso"));
		bookList.add(createBook("Java + Primefaces + iReport	Desenvolvendo um CRUD para web", null, 9788539904228L, 160, 1, "Brochura", 2013, "9788539904228", "Informática - Programação", "Português", "Editora Ciência Moderna", null));
		bookList.add(createBook("Relatórios Profissionais para Aplicações Java com as Ferramentas Ireport e Pentaho Design", null, 9788539903665L, 208, 1, "Brochura", 2013, "9788539903665", "Informática - Programação", "Português", "Editora Ciência Moderna", null));
		bookList.add(createBook("Guia Resumido para Certificação Oracle Java 7 SE Associate", null, 9788539905256L, 200, 1, "Brochura", 2014, "9788539905256", "Informática - Programação", "Português", "Editora Ciência Moderna", null));

		return bookList;
	}

	/**
	 * Create a book given the parameters
	 * 
	 * @param title
	 * @param subtitle
	 * @param ISBN
	 * @param pages
	 * @param edition
	 * @param bookType
	 * @param year
	 * @param barCode
	 * @param subject
	 * @param language
	 * @param publisher
	 * @param author
	 * 
	 * @return
	 */
	private static Book createBook(String title, String subtitle, Long ISBN, Integer pages, Integer edition, String bookType, Integer year, String barCode, String subject, String language, String publisher, String author) {
		Book book = new Book();

		book.setTitle(title);
		book.setSubtitle(subtitle);
		book.setISBN(ISBN);
		book.setPages(pages);
		book.setEdition(edition);
		book.setBookType(bookType);
		book.setYear(year);
		book.setSubject(subject);
		book.setLanguage(language);
		book.setBarCode(barCode);
		book.setPublisher(new Publisher(publisher));
		book.setAuthor(new Author(author));

		return book;
	}
}