package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;

class NameCri extends Criterion{
	public NameCri(String criName, String checkName) throws IllegalArgumentException {
		super(criName, AttrName.name, Op.contains, checkName);
	}

	public boolean assertCri(File file) {
		return super.val.toString().contains(file.getName());
	}
}
