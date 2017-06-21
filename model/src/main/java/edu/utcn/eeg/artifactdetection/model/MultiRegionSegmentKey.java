package edu.utcn.eeg.artifactdetection.model;

public class MultiRegionSegmentKey {
	private static final long serialVersionUID = 1L;
    private final int initIdx;
    private final int iterIdx;

	public MultiRegionSegmentKey(int iterIdx, int initIdx) {
		this.initIdx = initIdx;
		this.iterIdx = iterIdx;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
        result = prime * result + this.initIdx;
        result = prime * result + this.iterIdx;
        return result;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiRegionSegmentKey other = (MultiRegionSegmentKey) obj;
        if (this.initIdx != other.initIdx)
            return false;
        if (this.iterIdx != other.iterIdx)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Multikey_" + this.iterIdx + "_" + this.initIdx;
    }

}
