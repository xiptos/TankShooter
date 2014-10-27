package pt.ipb.game.engine;

/**
 *
 * @author Eric
 */
public class PerformanceMeter
{
    private final static long TIMER_RES = 1000 * 1000 * 1000;

    private enum Phase
    {
        NONE,
        UPDATE,
        DRAW,
        SLEEP
    }
    
    private Phase currentPhase = Phase.NONE;

    private double updateTime;
    private double drawTime;
    private double sleepTime;

    private int frameCount;
    private double fps;
    private int updateCount;
    private double ups;
    
    private long updateSum;
    private long drawSum;
    private long sleepSum;
    
    private long lastTime;
    
    public void beginUpdate()
    {
        nextPhase(Phase.UPDATE);
    }
    
    public void beginDraw()
    {
        nextPhase(Phase.DRAW);
    }
    
    public void beginSleep()
    {
        nextPhase(Phase.SLEEP);
    }

    public double getUpdateTime()
    {
        return updateTime;
    }

    public double getDrawTime()
    {
        return drawTime;
    }

    public double getSleepTime()
    {
        return sleepTime;
    }

    public double getFPS()
    {
        return fps;
    }

    public double getUPS()
    {
        return ups;
    }
    
    private void nextPhase(Phase next)
    {
        long time = System.nanoTime();
        long delta = time - lastTime;
        lastTime = time;
        if(delta < 0) delta = 0;
        
        switch(currentPhase)
        {
            case UPDATE:
                updateSum += delta;
                updateCount++;
                break;
            case DRAW:
                drawSum += delta;
                frameCount++;
                break;
            case SLEEP:
                sleepSum += delta;
                break;
            default:
                break;
        }
        currentPhase = next;

        long timeSum = updateSum + drawSum + sleepSum;
        if(timeSum >= TIMER_RES)
        {
            updateTime = (double)updateSum / (double)timeSum;
            drawTime = (double)drawSum / (double)timeSum;
            sleepTime = (double)sleepSum / (double)timeSum;

            fps = (double)frameCount / ((double)timeSum / (double)TIMER_RES);
            ups = (double)updateCount / ((double)timeSum / (double)TIMER_RES);

            updateSum = 0;
            drawSum = 0;
            sleepSum = 0;
            frameCount = 0;
            updateCount = 0;
        }
    }
}
