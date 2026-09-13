// PassengerCountTaxi.java
import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class PassengerCountTaxi {

    public static class MapperClass
        extends Mapper<Object,Text,IntWritable,IntWritable>{

        private final static IntWritable one = new IntWritable(1);

        public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

            String line = value.toString();
            if(line.startsWith("VendorID")) return;

            String[] f = line.split(",", -1);
            if(f.length < 19) return;

            try{
                int p = Integer.parseInt(f[3]);
                context.write(new IntWritable(p), one);
            }catch(Exception e){}
        }
    }

    public static class ReducerClass
        extends Reducer<IntWritable,IntWritable,
                        IntWritable,IntWritable>{

        public void reduce(IntWritable key,
            Iterable<IntWritable> values,
            Context context) throws IOException, InterruptedException {

            int sum = 0;
            for(IntWritable v: values) sum += v.get();

            context.write(key,new IntWritable(sum));
        }
    }

    public static void main(String[] args)throws Exception{
        Job job = Job.getInstance(new Configuration(),"Passenger");

        job.setJarByClass(PassengerCountTaxi.class);
        job.setMapperClass(MapperClass.class);
        job.setReducerClass(ReducerClass.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        System.exit(job.waitForCompletion(true)?0:1);
    }
}