package ejb.imple;

import dto.AverageData;
import dto.Gps;
import dto.TrafficDataDto;
import ejb.remote.TrafficDataProcess;
import jakarta.ejb.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class TrafficDataProcessBean implements TrafficDataProcess {
    private final TrafficDataBean tdb=new TrafficDataBean();

    @Override
    public List<AverageData> getAverage() {

        List<AverageData> avgData=new ArrayList<>();


        List<Gps> locations=tdb.getLocations();
        locations.forEach(l->{
           List<TrafficDataDto> data=tdb.getData(l.getLatitude(),l.getLongitude());
            final int[] count = {0};
           data.forEach(d->{
               count[0] +=d.getSpeed();
           });

           int average=count[0]/data.size();
           AverageData a=new AverageData();
           a.setLocation(l);
           a.setSpeed(average);

           avgData.add(a);

        });

        return avgData;
    }

    @Override
    public AverageData getAverage(Gps location) {
        List<TrafficDataDto> data=tdb.getData(location.getLatitude(),location.getLongitude());
        final int[] count = {0};
        data.forEach(d->{
            count[0] +=d.getSpeed();
        });

        int average=count[0]/(int)data.stream().count();
        AverageData a=new AverageData();
        a.setLocation(location);
        a.setSpeed(average);

        return a;
    }

    @Override
    public int getAllAverage() {
        List<Gps> locations=tdb.getLocations();
        final int[] count = {0};
        locations.forEach(l->{
            List<TrafficDataDto> data=tdb.getData(l.getLatitude(),l.getLongitude());
            data.forEach(d->{
                count[0] +=d.getSpeed();
            });
        });
        return count[0]/tdb.getData().size();
    }

    @Override
    public List<AverageData> getMaxSpeeds() {
        List<AverageData> maxSpeedData=new ArrayList<>();


        List<Gps> locations=tdb.getLocations();
        locations.forEach(l->{
            final int[] maxSpeed= {0};
            List<TrafficDataDto> data=tdb.getData(l.getLatitude(),l.getLongitude());
            data.forEach(d->{
                if(maxSpeed[0]<d.getSpeed()){
                    maxSpeed[0]=d.getSpeed();
                }
            });


            AverageData a=new AverageData();
            a.setLocation(l);
            a.setSpeed(maxSpeed[0]);

            maxSpeedData.add(a);

        });

        return maxSpeedData;
    }

    @Override
    public AverageData getMaxSpeeds(Gps location) {
       final int[] maxSpeed= {0};
        List<TrafficDataDto> data=tdb.getData(location.getLatitude(),location.getLongitude());
        data.forEach(d->{
            if(maxSpeed[0]<d.getSpeed()){
                maxSpeed[0]=d.getSpeed();
            }
        });


        AverageData a=new AverageData();
        a.setLocation(location);
        a.setSpeed(maxSpeed[0]);

        return a;
    }

    @Override
    public AverageData getMaxAllSpeeds() {
        List<Gps> locations=tdb.getLocations();
        final int[] maxSpeed = {0};
        AverageData a=new AverageData();

        locations.forEach(l->{
            List<TrafficDataDto> data=tdb.getData(l.getLatitude(),l.getLongitude());
            data.forEach(d->{
              if(maxSpeed[0] <d.getSpeed()){
                  maxSpeed[0]=d.getSpeed();
                  a.setLocation(l);
              }
            });
        });

        a.setSpeed(maxSpeed[0]);

        return a;
    }

    @Override
    public List<AverageData> getMinSpeeds() {
        List<AverageData> maxSpeedData=new ArrayList<>();


        List<Gps> locations=tdb.getLocations();
        locations.forEach(l->{
            final int[] minSpeed= {260};
            List<TrafficDataDto> data=tdb.getData(l.getLatitude(),l.getLongitude());
            data.forEach(d->{
                if(minSpeed[0]>d.getSpeed()  && d.getSpeed()!=0){
                    minSpeed[0]=d.getSpeed();
                }
            });


            AverageData a=new AverageData();
            a.setLocation(l);
            a.setSpeed(minSpeed[0]);

            maxSpeedData.add(a);

        });

        return maxSpeedData;
    }

    @Override
    public AverageData getMinSpeeds(Gps location) {
        final int[] minSpeed= {260};
        List<TrafficDataDto> data=tdb.getData(location.getLatitude(),location.getLongitude());
        data.forEach(d->{
            if(minSpeed[0]>d.getSpeed()  && d.getSpeed()!=0){
                minSpeed[0]=d.getSpeed();
            }
        });


        AverageData a=new AverageData();
        a.setLocation(location);
        a.setSpeed(minSpeed[0]);

        return a;
    }

    @Override
    public AverageData getMinAllSpeeds() {
        List<Gps> locations=tdb.getLocations();
        final int[] minSpeed = {260};
        AverageData a=new AverageData();

        locations.forEach(l->{
            List<TrafficDataDto> data=tdb.getData(l.getLatitude(),l.getLongitude());
            data.forEach(d->{
                if(minSpeed[0] >d.getSpeed() && d.getSpeed()!=0){
                    minSpeed[0]=d.getSpeed();
                    a.setLocation(l);
                }
            });
        });

        a.setSpeed(minSpeed[0]);

        return a;
    }
}
