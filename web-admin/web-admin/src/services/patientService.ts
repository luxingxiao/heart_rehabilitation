export type PatientStatus = 'active' | 'inactive' | 'pending';

export interface Patient {
  id: string;
  name: string;
  gender: 'male' | 'female' | 'other';
  age: number;
  phone?: string;
  email?: string;
  address?: string;
  status: PatientStatus;
  lastVisit: string;
  createdAt: string;
  updatedAt: string;
}

// Mock data
const mockPatients: Patient[] = [
  {
    id: 'P001',
    name: 'Zhang San',
    gender: 'male',
    age: 45,
    phone: '13800138000',
    email: 'zhangsan@example.com',
    address: 'Beijing',
    status: 'active',
    lastVisit: '2026-02-15',
    createdAt: '2025-01-10',
    updatedAt: '2026-02-15',
  },
  {
    id: 'P002',
    name: 'Li Si',
    gender: 'female',
    age: 32,
    phone: '13900139000',
    email: 'lisi@example.com',
    address: 'Shanghai',
    status: 'active',
    lastVisit: '2026-02-10',
    createdAt: '2025-03-20',
    updatedAt: '2026-02-10',
  },
  {
    id: 'P003',
    name: 'Wang Wu',
    gender: 'male',
    age: 58,
    phone: '13700137000',
    email: 'wangwu@example.com',
    address: 'Guangzhou',
    status: 'inactive',
    lastVisit: '2025-12-05',
    createdAt: '2024-11-15',
    updatedAt: '2025-12-05',
  },
  {
    id: 'P004',
    name: 'Zhao Liu',
    gender: 'female',
    age: 29,
    phone: '13600136000',
    email: 'zhaoliu@example.com',
    address: 'Shenzhen',
    status: 'pending',
    lastVisit: '2026-01-20',
    createdAt: '2025-12-01',
    updatedAt: '2026-01-20',
  },
  {
    id: 'P005',
    name: 'Qian Qi',
    gender: 'other',
    age: 36,
    phone: '13500135000',
    email: 'qianqi@example.com',
    address: 'Hangzhou',
    status: 'active',
    lastVisit: '2026-02-28',
    createdAt: '2025-06-10',
    updatedAt: '2026-02-28',
  },
];

// Simulate network delay
const delay = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));

class PatientService {
  private patients: Patient[] = [...mockPatients];

  async getPatients(): Promise<Patient[]> {
    await delay(300);
    return this.patients;
  }

  async getPatient(id: string): Promise<Patient | undefined> {
    await delay(200);
    return this.patients.find(p => p.id === id);
  }

  async createPatient(patient: Omit<Patient, 'id' | 'createdAt' | 'updatedAt' | 'lastVisit'>): Promise<Patient> {
    await delay(400);
    const newPatient: Patient = {
      ...patient,
      id: `P${String(this.patients.length + 1).padStart(3, '0')}`,
      lastVisit: new Date().toISOString().split('T')[0],
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };
    this.patients.push(newPatient);
    return newPatient;
  }

  async updatePatient(id: string, updates: Partial<Omit<Patient, 'id' | 'createdAt' | 'updatedAt'>>): Promise<Patient | undefined> {
    await delay(400);
    const index = this.patients.findIndex(p => p.id === id);
    if (index === -1) return undefined;
    const updated = {
      ...this.patients[index],
      ...updates,
      updatedAt: new Date().toISOString(),
    };
    this.patients[index] = updated;
    return updated;
  }

  async deletePatient(id: string): Promise<boolean> {
    await delay(300);
    const initialLength = this.patients.length;
    this.patients = this.patients.filter(p => p.id !== id);
    return this.patients.length < initialLength;
  }
}

export default new PatientService();