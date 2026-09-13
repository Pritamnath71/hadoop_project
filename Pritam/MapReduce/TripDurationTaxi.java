// TripDurationTaxi.java
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class TripDurationTaxi {

    public static class MapperClass
        extends Mapper<Object,Text,Text,Text>{

        SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

            String line = value.toString();
            if(line.startsWith("VendorID")) return;

            String[] f = line.split(",", -1);
            if(f.length < 19) return;

            try{
                Date pick = sdf.parse(f[1]);
                Date drop = sdf.parse(f[2]);

                long diff = (drop.getTime() - pick.getTime())/60000;

                context.write(new Text("ALL"),
                    new Text(diff + ",1"));

            }catch(Exception e){}
        }
    }

    public static class ReducerClass
        extends Reducer<Text,Text,Text,Text>{

        public void reduce(Text key, Iterable<Text> values,
            Context context) throws IOException, InterruptedException {

            double sum=0; int count=0;

            for(Text val: values){
                String[] p=val.toString().split(",");
                sum+=Double.parseDouble(p[0]);
                count++;
            }

            context.write(key,
                new Text("avg_duration=" + (sum/count)));
        }
    }

    public static void main(String[] args)throws Exception{
        Job job = Job.getInstance(new Configuration(),"Duration");

        job.setJarByClass(TripDurationTaxi.class);
        job.setMapperClass(MapperClass.class);
        job.setReducerClass(ReducerClass.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        System.exit(job.waitForCompletion(true)?0:1);
    }
}