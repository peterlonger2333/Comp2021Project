package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;

class SizeCri extends Criterion{
	public SizeCri(String criName, Op op, int size) throws IllegalArgumentException {
		super(criName, AttrName.size, op, size);
	}

	@Override
	public
	boolean assertCri(File file) {
		// TODO Auto-generated method stub
		return false;
	}
}
