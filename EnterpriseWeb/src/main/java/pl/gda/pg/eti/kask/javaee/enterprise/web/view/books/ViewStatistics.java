package pl.gda.pg.eti.kask.javaee.enterprise.web.view.books;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import pl.gda.pg.eti.kask.javaee.enterprise.books.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ViewStatistics implements Serializable {

    @EJB
    BookService bookService;

    public BarChartModel getBarChartModel() {
        BarChartModel barChartModel = new BarChartModel();
        ChartSeries authors = new ChartSeries();

        Map<Author, Long> booksCountByAuthor = bookService.countBooksPerAuthor();
        booksCountByAuthor.forEach(
                (author, booksCount) ->
                        authors.set(author.getFullName(), booksCount));

        barChartModel.addSeries(authors);
        barChartModel.getAxis(AxisType.Y).setLabel("Books");

        return barChartModel;
    }

    public PieChartModel getPieChartModel() {
        PieChartModel pieChartModel = new PieChartModel();
        pieChartModel.setLegendPosition("w");
        pieChartModel.setShowDataLabels(true);

        Map<Author, Long> booksCountByAuthor = bookService.countBooksPerAuthor();
        booksCountByAuthor.forEach(
                (author, booksCount) ->
                        pieChartModel.set(author.getFullName(), booksCount));

        return pieChartModel;
    }

    public TimelineModel getTimelineModel() {
        TimelineModel model = new TimelineModel();

        List<Book> books = bookService.findAllBooks();
        books.forEach(book -> model.add(
                new TimelineEvent(book.getTitle(), book.getPublishDate())));

        return model;
    }
}
