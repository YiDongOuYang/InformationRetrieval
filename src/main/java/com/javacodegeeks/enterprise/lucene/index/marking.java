package com.javacodegeeks.enterprise.lucene.index;

public class marking {
	public static String[] getTitle() {
		String[] title = new String[50];
		title[0] = "Recent Avian H5N1 Viruses Exhibit Increased Propensity for Acquiring Human Receptor Specificity";
		title[1] = "Identifying synergistic regulation involving c-Myc and sp1 in human tissues";
		title[2] = "Dynamic Magnetic Resonance Inverse Imaging of Human Brain Function";
		title[3] = "A single mutation in the 729 residue modulates human DNA topoisomerase IB DNA binding and drug resistance";
		title[4] = "Real-time assembly and disassembly of human RAD51 filaments on individual DNA molecules";
		title[5] = "HumanEva: Synchronized Video and Motion Capture Dataset and Baseline Algorithm for Evaluation of Articulated Human Motion";
		title[6] = "Human Papillomavirus Type 16 E7 E-mail alerts Antitumor Activity of Human Papillomavirus Type 16 E7-Specific T Cells against Virally Infected Squamous Cell Carcinoma of the Head and Neck";
		title[7] = "Chromosome-specific and noisy IFNB1 transcription in individual virus-infected human primary dendritic cells";
		title[8] = "Proteins of the nuclear factor-1 family act as an activator of the late promoter in human polyomavirus BK in vitro";
		title[9] = "Chronic interaction between humans and free-ranging bottlenose dolphins near Panama City Beach, Florida, USA";
		title[10] = "Aral Sea Water Rights";
		title[11] = "CONSTRUCTED WETLANDS FOR ANIMAL WASTE WATER TREATMENT";
		//标题重复？？？
		title[12] = "TENSION LYSIMETERS: MONITORING OF PESTICIDES IN SOIL WATER Environmental Hazards Assessment Program";
		title[13] = "The chemical composition of shallow-water hydrothermal fluids in Tutum Bay, Ambitle Island, Papua New Guinea and their effect on ambient seawater";
		title[14] = "Factors responsible for high arsenic concentrations in two groundwater catchments in Taiwan";
		title[15] = "Biogeographic Relationships of a Rocky Intertidal Fish Assemblage in an Area of Cold Water Upwelling off Baja California, Mexico!";
		title[16] = "Pan-European, invasive species of Hypocrea/Trichoderma Antimicrobial resistance and multicellular swarming motility SAR11 glycine-activated riboswitch and auxotrophy Metagenomic and stable isotopic analyses of modern freshwater microbialites in Cuatro Ci\\'e9negas, Mexico";
		title[17] = "Water Sensitive Urb a n D e s i g n -t h e Jo u r n e y T hus Far Actions Towar ds Sustaina ble Outcomes";
		title[18] = "Dinoflagellate cysts as indicators of water quality and productivity in British Columbia estuarine environments";
		title[19] = "FLORIDA SEA GRANT COLLEGE PROGRAM R/LRcAc20 Aquaculture and Marketing of the Florida Bay Scallop in Crystal River, Florida with special reports on Marketing Analysis Economic Analysis";
		title[20] = "Annotating Texts for Language Documentation with Discourse Profiler's Metatagging System";
		title[21] = "A Bigger Picture: Information Systems and Spatial Data Infrastructure Research Perspectives";
		title[22] = "TARGETED, SYSTEMIC NON-VIRAL DELIVERY OF SMALL INTERFERING RNA IN VIVO Thesis by";
		title[23] = "Practical Issues in a Virtual Sculptor System for Rapid Modeling";
		title[24] = "Rapid conditional knock-down-knock-in system for mammalian cells";
		title[25] = "SNPLogic: an interactive single nucleotide polymorphism selection, annotation, and prioritization system";
		title[26] = "An Operating System Development Environment An Operating System Development Environment";
		title[27] = "STREAMROLLER : A UNIFIED COMPILATION AND SYNTHESIS SYSTEM FOR STREAMING APPLICATIONS";
		title[28] = "An Introduction to Assertional Reasoning for Concurrent Systems";
		title[29] = "Lehrstuhl f\\'fcr Informatik 10 (Systemsimulation) A parallel K-SVD implementation for CT image denoising A parallel K-SVD implementation for CT image denoising";
		title[30] = "Intracellular expression profiles measured by real-time PCR tomography in the Xenopus laevis oocyte";
		title[31] = "Expression of the Leo1-like domain of replicative senescence down-regulated Leo1-like (RDL) protein promotes senescence of 2BS fibroblasts";
		title[32] = "The Expression of D-Cyclin Genes Defines Distinct Developmental Zones in Snapdragon Apical Meristems and Is Locally Regulated by the Cycloidea Gene 1";
		title[33] = "Evolution of Crystallins: Expression of Lens-Specific Proteins in the Blind Mammals Mole (Talpa europaea) and Mole Rat (Spalax eIwe&ergi)'";
		title[34] = "The Mouse Aire Gene: Comparative Genomic Sequencing, Gene Organization, and Expression";
		title[35] = "Whole genome exon arrays identify differential expression of alternatively spliced, cancer-related genes in lung cancer";
		title[36] = "Unique and Overlapping Expression Patterns among Members of Photosynthesis-Associated Nuclear Gene Families in Arabidopsis 1[W][OA]";
		title[37] = "Molecular Characterization of Potato Fumarate Hydratase and Functional Expression in Escherichia COE'";
		title[38] = "Remodeling of chromatin structure within the promoter is important for bmp-2-induced fgfr3 expression";
		title[39] = "DNA methyltransferase 3B (DNMT3B) mutations in ICF syndrome lead to altered epigenetic modifications and aberrant expression of genes regulating development, neurogenesis and immune function";
		title[40] = "Piecewise Planar Stereo for Image-based Rendering";
		title[41] = "Image-Based Acquisition of Shape and Spatially Varying Reflectance";
		title[42] = "Dense Image Registration through MRFs and Efficient Linear Programming";
		title[43] = "Piecewise Planar Stereo for Image-based Rendering";
		title[44] = "Distance-Free Image Retrieval Based on Stochastic Diffusion over Bipartite Graphs";
		title[45] = "New Appearance Models for Natural Image Matting";
		title[46] = "Interactive Techniques for Registering Images to Digital Terrain and Building Models";
		title[47] = "RUSSIA: GREAT POWER IMAGE VERSUS ECONOMIC REALITY";
		title[48] = "Lehrstuhl f\\'fcr Informatik 10 (Systemsimulation) A parallel K-SVD implementation for CT image denoising A parallel K-SVD implementation for CT image denoising";
		title[49] = "AUTOMATIC DETECTION OF CHANGES FROM LASER SCANNER AND AERIAL IMAGE DATA FOR UPDATING BUILDING MAPS";
		
		
		return title;
		//共查询human,water,system,expression,image等5个关键词，每个关键词有10个相关文档；文档集有50个，与human相关的文档在0-9位，以此类推。
	}
	public static float[][]getScore() {
		float[][]score=new float[5][50];
		float[] human = new float[50];
		for(int i = 0; i < 50; i++) {
			if(0<=i && i<=9)
				human[i] = 1;
			else
				human[i] = 0;
		}
		
		
		float[] water = new float [50];
		for(int i = 0; i < 50; i++) {
			if(10<=i && i<=19)
				water[i] = 1;
			else
				water[i] = 0;
		}
		
		float[] system = new float[50];
		for(int i = 0; i < 50; i++) {
			if(20<=i && i<=29)
				system[i] = 1;
			else
				system[i] = 0;
		}
		
		float[] expression = new float[50];
		for(int i = 0; i < 50; i++) {
			if(30<=i && i<=39)
				expression[i] = 1;
			else
				expression[i] = 0;
		}
		
		float[] image = new float[50];
		for(int i = 0; i < 50; i++) {
			if(40<=i && i<=49)
				image[i] = 1;
			else
				image[i] = 0;
		}
		
		score[0]=human;
		score[1]=water;
		score[2]=system;
		score[3]=expression;
		score[4]=image;
		return score;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
//		for(int i = 0; i < 50; i++) {
//			System.out.println(i + "." + title[i]);
//			System.out.println("human:" + human[i] + " water:" + water[i] + " system:" + system[i] + " expression:" + expression[i] + " image:" + image[i]);
//			System.out.println();
//		}
	}

}
