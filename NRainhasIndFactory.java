public class NRainhasIndFactory implements IndFactory {
    private int m;
    
    public NRainhasIndFactory(int m) {
        this.m = m;
    }
    
    @Override
    public Ind getInstance() {
        return new NRainhasInd(this.m);
    }
}
