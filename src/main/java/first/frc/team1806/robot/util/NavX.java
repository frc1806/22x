package first.frc.team1806.robot.util;


import com.kauailabs.navx.AHRSProtocol;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

/**
 * Driver for a NavX board. Basically a wrapper for the {@link AHRS} class
 */
public class NavX {

    protected class Callback implements ITimestampedDataSubscriber {
        @Override
        public void timestampedDataReceived(long system_timestamp, long sensor_timestamp, AHRSProtocol.AHRSUpdateBase update,
                Object context) {
            synchronized (NavX.this) {
                // This handles the fact that the sensor is inverted from our coordinate conventions.
                if (mLastSensorTimestampMs != kInvalidTimestamp && mLastSensorTimestampMs < sensor_timestamp) {
                    mYawRateDegreesPerSecond = 1000.0 * (-mYawDegrees - update.yaw)
                            / (double) (sensor_timestamp - mLastSensorTimestampMs);
                }
                mLastSensorTimestampMs = sensor_timestamp;
                mYawDegrees = -update.yaw;
//                System.out.println(-update.yaw);
            }
        }
    }

    protected AHRS mAHRS;

    protected Rotation2d mAngleAdjustment = Rotation2d.identity();
    protected Rotation2d mResetAngle = Rotation2d.identity();
    protected double mYawDegrees;
    protected double mYawRateDegreesPerSecond;
    protected final long kInvalidTimestamp = -1;
    protected long mLastSensorTimestampMs;

    public NavX(SPI.Port spi_port_id) {
        mAHRS = new AHRS(spi_port_id, (byte) 200);
        //mAHRS = new AHRS(SerialPort.Port.kUSB);
        resetState();
        mAHRS.registerCallback(new Callback(), null);
    }

    public synchronized void reset() {
        mAHRS.reset();
        resetState();
    }

    public synchronized void zeroYaw() {
        mResetAngle = Rotation2d.fromDegrees(-getRawYawDegrees());
        resetState();
    }

    private void resetState() {
        mLastSensorTimestampMs = kInvalidTimestamp;
        mYawDegrees = 0.0;
        mYawRateDegreesPerSecond = 0.0;
    }

    public synchronized void setAngleAdjustment(Rotation2d adjustment) {
        mAngleAdjustment = adjustment;
    }

    protected synchronized double getRawYawDegrees() {
        return mYawDegrees;
    }

    public Rotation2d getYaw() {
        return mAngleAdjustment.rotateBy(Rotation2d.fromDegrees(getRawYawDegrees()).rotateBy(mResetAngle));
    }

    public double getRoll() {
        return mAHRS.getRoll();
    }

    public double getYawRateDegreesPerSec() {
        return mYawRateDegreesPerSecond;
    }

    public double getYawRateRadiansPerSec() {
        return 180.0 / Math.PI * getYawRateDegreesPerSec();
    }

    public double getRawAccelX() {
        return mAHRS.getRawAccelX();
    }

    public float getWorldLinearAccelX(){
        return mAHRS.getWorldLinearAccelX();
    }

    public float getWorldLinearAccelY(){
        return mAHRS.getWorldLinearAccelY();
    }

    public float getWorldLinearAccelZ(){
        return mAHRS.getWorldLinearAccelZ();
    }
}
