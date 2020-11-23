package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;

class SizeCri extends Criterion{
	Op comparator;
	int comparand;
	
	public SizeCri(String criName, Op op, int size) throws IllegalArgumentException {
		super(criName, AttrName.size, op, size);
		this.comparator = op;
		this.comparand = size;
	}

	@Override
	public boolean assertCri(File file) {
		switch (comparator) {
		case E:
			return file.getSize() == comparand;
		case G:
			return file.getSize() > comparand;
		case GE:
			return file.getSize() >= comparand;
		case L:
			return file.getSize() < comparand;
		case LE:
			return file.getSize() <= comparand;
		case NE:
			return file.getSize() != comparand;
		default:
			//never reached
			return false;
		}
	}
}
