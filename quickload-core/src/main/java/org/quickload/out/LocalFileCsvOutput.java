package org.quickload.out;

import org.quickload.config.Config;
import org.quickload.config.ConfigSource;
import org.quickload.config.DynamicModel;
import org.quickload.exec.BufferManager;
import org.quickload.record.*;
import org.quickload.spi.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class LocalFileCsvOutput
        extends BasicOutputPlugin<LocalFileCsvOutput.Task>
{ // TODO change superclass to FileOutputPlugin

    // TODO use DynamicTask
    public interface Task
            extends OutputTask, DynamicModel<Task>
    { // TODO change superclass to FileOutputTask
        @Config("out:paths")
        public List<String> getPaths();

        @Config("out:schema")
        public Schema getSchema();
    }

    public static class Operator
            extends AbstractOutputOperator
    {
        private final Task task;
        private final int processorIndex;
        private final PageAllocator pageAllocator;

        Operator(Task task, int processorIndex) {
            this.task = task;
            this.processorIndex = processorIndex;
            this.pageAllocator = new BufferManager(); // TODO
        }

        @Override
        public void addPage(Page page)
        {
            // TODO ad-hoc
            String path = task.getPaths().get(processorIndex);
            // TODO manually create schema object now
            Schema schema = new Schema(Arrays.asList(
                    new Column(0, "date_code", StringType.STRING),
                    new Column(1, "customer_code", StringType.STRING),
                    new Column(2, "product_code", StringType.STRING),
                    new Column(3, "employee_code", StringType.STRING)));

            // TODO simple implementation

            PageReader pageReader = new PageReader(pageAllocator, schema);
            RecordCursor recordCursor = pageReader.cursor(page);
            File file = new File(path);

            try (PrintWriter w = new PrintWriter(file)) {
                // TODO writing data to the file

                while (recordCursor.next()) {
                    RecordConsumer recordConsumer = new RecordConsumer() {
                        @Override
                        public void setNull(Column column) {
                            // TODO
                        }

                        @Override
                        public void setLong(Column column, long value) {
                            // TODO
                        }

                        @Override
                        public void setDouble(Column column, double value) {
                            // TODO
                        }

                        @Override
                        public void setString(Column column, String value) {
                            System.out.print(value);
                            System.out.print(',');
                            //w.append(value).append(',');
                        }
                    };
                    schema.consume(recordCursor, recordConsumer);
                    System.out.println();
                    //w.append('\n');
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close() throws Exception
        {
        }

        @Override
        public Report completed()
        {
            return DynamicReport.builder().build(null);
        }
    }

    @Override
    public Task getTask(ConfigSource config, InputTask input)
    {
        Task task = config.load(Task.class);
        return task.validate();
    }

    @Override
    public void begin(Task task)
    {
    }

    @Override
    public Operator openOperator(Task task, int processorIndex)
    {
        return new Operator(task, processorIndex);
    }

    @Override
    public void commit(Task task, List<Report> reports)
    {
    }

    @Override
    public void abort(Task task)
    {
    }
}